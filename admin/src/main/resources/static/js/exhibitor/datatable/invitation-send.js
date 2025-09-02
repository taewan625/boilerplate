window.addEventListener('load', () => {
    const exhibitorId = getExhibitorId();

    setSendDatatable(exhibitorId);

    $('#invitation-send-datatable tbody').on('click', '.btn-delete', function () {
        const row = sendDatatable.row($(this).parents('tr'));
        const rowData = row.data();
        row.remove().draw(false);
    })


    const requiredHeaders = ['TYPE', 'EMAIL', 'NAME', 'COMPANY', 'JOB-TITLE', 'COUNTRY', 'CITY', 'PHONE-NUMBER'];
    $("#file").change((e)=>{
        const fileEvent = e;
        const file = e.target.files[0];
        if (!file) return;

        const allowedTypes = [
            'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
            'application/vnd.ms-excel'
        ];
        if (!allowedTypes.includes(file.type)) {
            toastr.error('엑셀 파일 (.xlsx 또는 .xls)만 업로드 가능합니다.');
            return;
        }


        const reader = new FileReader();
        reader.onload = function(readerEvent) {
            try {
                const data = new Uint8Array(readerEvent.target.result);
                const workbook = XLSX.read(data, {type: 'array'});

                const firstSheetName = workbook.SheetNames[0];
                const worksheet = workbook.Sheets[firstSheetName];
                const jsonData = XLSX.utils.sheet_to_json(worksheet, {range:1, header:1});


                const actualHeaders = jsonData[0] || {};

                const missingHeaders = requiredHeaders.filter(h => !actualHeaders.includes(h));

                if (missingHeaders.length > 0) {
                    toastr.error(`엑셀에 다음 컬럼이 없습니다: ${missingHeaders.join(', ')}`);
                    fileEvent.target.value = '';
                    return;
                }
            }catch (err) {
                toastr.error('엑셀 파일을 읽는 중 오류가 발생했습니다.');
                fileEvent.target.value = '';
                console.error(err);
            }
        }
        reader.readAsArrayBuffer(file);
    });


    $('#btn-excel-update').click(()=>{
        const file = $('#file')[0].files[0];
        if(!file){
            toastr.error('파일을 선택해주세요.');
            return;
        }


        const reader = new FileReader();
        reader.onload = function(readerEvent) {
            try {
                const data = new Uint8Array(readerEvent.target.result);
                const workbook = XLSX.read(data, {type: 'array'});

                const firstSheetName = workbook.SheetNames[0];
                const worksheet = workbook.Sheets[firstSheetName];
                const jsonData = XLSX.utils.sheet_to_json(worksheet, {range:1, header:requiredHeaders});

                const actualHeaders = jsonData[0] || {};


                // 유효성 검사
                const invalidRows = [];
                const validData = [];

                console.log(jsonData)

                jsonData.forEach((row, index) => {
                    if(index === 0) return;

                    const rowNum = index + 2; // 엑셀 1행은 헤더니까 +2

                    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

                    let rowErrors = [];

                    const type = safeTrim(row["TYPE"]);
                    const email = safeTrim(row["EMAIL"]);
                    const name = safeTrim(row["NAME"]);
                    let company = row["COMPANY"];
                    const jobTitle = safeTrim(row["JOB-TITLE"]);
                    const country = safeTrim(row["COUNTRY"]);
                    const city = safeTrim(row["CITY"]);
                    const phoneNumber = safeTrim(row["PHONE-NUMBER"]);


                    // 숫자인 경우 문자열로 변환
                    if (typeof company === 'number') {
                        company = company.toString();
                    }

                    // 값이 문자열이 아닌 경우 안전하게 변환
                    company = safeTrim(company);

                    // 유효성 검사
                    if (type === '' || !['EXHIBITOR_BADGE', 'INVITATION'].includes(type)) rowErrors.push('TYPE');
                    if (email === '' || !emailRegex.test(email)) rowErrors.push('EMAIL');
                    if (name === '') rowErrors.push('NAME');
                    if (company === '') rowErrors.push('COMPANY'); // 빈 값 확인


                    if (rowErrors.length > 0) {
                        invalidRows.push([`Row ${rowNum}: ${rowErrors.join(', ')}`]);
                    } else {
                        validData.push({
                            type: type,
                            email: email,
                            name: name,
                            company: company,
                            jobTitle : jobTitle ? jobTitle : null,
                            country:  country ? country : null,
                            city : city ? city : null,
                            phoneNumber : phoneNumber ? phoneNumber : null,
                        });
                    }
                });


                if(invalidRows.length > 0){
                    toastr.error(`${invalidRows.join(", <br />")}`);
                    $("#file").val("");
                    return;
                }

                sendDatatable.rows.add(validData).draw(false);
                $("#file").val("");

            }catch (err) {
                toastr.error('엑셀 파일을 읽는 중 오류가 발생했습니다.');
                console.error(err);
            }
        }
        reader.readAsArrayBuffer(file);

    });

    function safeTrim(value) {
        return typeof value === 'string' ? value.trim() : '';
    }

    $("#btn-table-refresh").click(()=>{
        $("#file").val("");
        sendDatatable.clear().draw(false);
    })


    document.querySelectorAll('button[data-bs-toggle="tab"]').forEach(tabBtn => {
        tabBtn.addEventListener('shown.bs.tab', function (e) {
            const targetId = e.target.getAttribute('data-bs-target');
            $("#file").val("");
            $("#search-text").val('');
            $("#search-type").val('E').trigger('change');

            sendDatatable.clear().draw(false);
            historyDatatable.ajax.reload();



        });
    });


    $('#btn-send').click(() =>{
        const sendData = sendDatatable.rows().data().toArray();

        if(sendData.length < 1){
            toastr.error(`발송할 데이터를 입력해주세요.`);
            return;
        }else{
            if(confirm("정말로 초대권을 발송하시겠습니까?")){
                sendInvitation(exhibitorId, sendData);
            }
        }



    });


    loadingExit();
});
let sendDatatable;
let beforeBadgeSendCount = 0;
let beforeInvitationSendCount = 0;

const setSendDatatable = (id) => {
    sendDatatable = $("#invitation-send-datatable").DataTable({
        searching: false,
        info: true,   //하단 페이지 수 비활성화
        infoCallback: (settings, start, end, max, total, pre) =>{
            // 현재 테이블 전체 데이터 가져오기
            const data = settings.aoData
                ?.filter(item => item && item._aData)   // null 체크
                .map(item => item._aData) || []; // 전체 row 데이터 추출

            // type별 개수 세기
            const typeCount = {};
            data.forEach(row => {
                const type = row.type || '-';
                typeCount[type] = (typeCount[type] || 0) + 1;
            });

            beforeBadgeSendCount= typeCount["EXHIBITOR_BADGE"] || 0;
            beforeInvitationSendCount= typeCount["INVITATION"] || 0;

            const totalBadeSendCount = badgeSendCount+beforeBadgeSendCount;
            const totalInvitationSendCount = invitationSendCount+beforeInvitationSendCount;

            $("#sendBadgeCount").val(totalBadeSendCount);
            $("#sendInvitationCount").val(totalInvitationSendCount);

            if(totalBadeSendCount > $("#badgeCount").val() || totalInvitationSendCount > $('#invitationCount').val()){
              $('#btn-send').attr("disabled", "disabled");
            }else{
                $('#btn-send').removeAttr("disabled");
            }

            // 문자열로 변환 (예: "GUEST: 3, STAFF: 2")
            const typeInfo = Object.entries(typeCount)
                .map(([type, count]) => `${type}: ${count}`)
                .join(' / ');

            if(max == 0){
                return ``;
            }else{
                return `Total ${max} | ${typeInfo}`;
            }


        },//  엔트리 갯수 custom
        lengthChange: false, // 상단 엔트리 개수 설정 비활성화
        language: {   //로딩 중 문자 수정
            processing :""
        },
        paging: false,
        bPaginate: false,
        responsive: true,
        processing: false,
        serverSide: false,
        order: [],
        columns: [
            { name : "TYPE", title : "TYPE", data : "type", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "EMAIL", title : "EMAIL", data : "email", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "NAME", title : "NAME", data : "name", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "COMPANY", title : "COMPANY", data : "company", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "JOBTITLE", title : "JOB TITLE", data : "jobTitle", className : "dt-head-center dt-body-left px-2", orderable: false,
                render: (data, type, row) =>{
                    if(!data){
                        return '-';
                    }
                    return data;
                }
            },
            { name : "COUNTRY", title : "COUNTRY", data : "country", className : "dt-head-center dt-body-left px-2", orderable: false,
                render: (data, type, row) =>{
                    if(!data){
                        return '-';
                    }
                    return data;
                }
            },
            { name : "CITY", title : "CITY", data : "city", className : "dt-head-center dt-body-left px-2", orderable: false,
                render: (data, type, row) =>{
                    if(!data){
                        return '-';
                    }
                    return data;
                }
            },
            { name : "PHONENUMBER", title : "PHONE", data : "phoneNumber", className : "dt-head-center dt-body-left px-2", orderable: false,
                render: (data, type, row) =>{
                    if(!data){
                        return '-';
                    }
                    return data;
                }
            },
            { name : "ACTION", title : "", data : "id", className : "dt-head-center dt-body-center px-2", orderable: false,
                render: (data, type, row) =>{
                    return `<button type="button" class="btn btn-outline-danger border-0 btn-delete"><i class='bx bxs-minus-square'></i></button>`
                }
            },

        ],
        fnRowCallback: (nRow, aData, iDisplayIndex)=> {},
        columnDefs:[
            {targets:[0], width:"9%", padding:"0px"}
            ,{targets:[1], width:"17%", padding:"0px"}
            ,{targets:[2], width:"12%", padding:"0px"}
            ,{targets:[3], width:"15%", padding:"0px"}
            ,{targets:[4], width:"15%", padding:"0px"}
            ,{targets:[5], width:"9%", padding:"0px"}
            ,{targets:[6], width:"8%", padding:"0px"}
            ,{targets:[7], width:"10%", padding:"0px"}
            ,{targets:[8], width:"5%", padding:"0px"}

        ],
        initComplete: () =>{
            appendSendInputRow(id);
        },
        drawCallback: () =>{
            appendSendInputRow(id);
        }
    });
}

const sendInvitation = (exhibitorId, sendData) => {
    const data = sendData.map((row, index) => ({
        ...row, // 기존 row 데이터 유지
        exhibitionId : $('#exhibition option:selected').val()
    }));


    $.ajax({
        type: "POST",
        url: `/api/v1/admin/exhibitor/invitation/${exhibitorId}`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        success: (res) => {
            if (res.success) {
                toastr.success('정상적으로 처리되었습니다.');
                sendDatatable.clear().draw(false);
                getExhibitor(exhibitorId);
            } else {
                toastr.error('실패 하였습니다.');
            }
        },
        beforeSend: () => { loadingStart() },
        complete: () => { loadingExit() },
        error: () => { toastr.error('서버 오류 발생'); }
    });

}



const isEmpty = (value) => {
    return !value || value.trim() === "";
}

const isValidEmail = (email) => {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
};

const appendSendInputRow = (id) => {
    // 이미 있으면 다시 안 붙이도록 체크
    if ($('#send-input-row').length === 0) {
        $('#invitation-send-datatable tbody').prepend(`
            <tr id="send-input-row">
                <form id="send-input-row-form" onsubmit="return false;">
                    <td class="px-2">
                            <select id="invitationType" class="form-select">
                              <option value="EXHIBITOR_BADGE" selected>Exhibitor Badge</option>
                              <option value="INVITATION">Invitation</option>    
                            </select>
                    </td>
                    <td class="px-2"><input type="email" class="form-control" id="send-email" placeholder="email"></td>
                    <td class="px-2"><input type="text" class="form-control" id="send-name" placeholder="name"></td>
                    <td class="px-2"><input type="text" class="form-control" id="send-company" placeholder="company"></td>
                    <td class="px-2"><input type="text" class="form-control" id="send-job-title" placeholder="job title"></td>
                    <td class="px-2"><input type="text" class="form-control" id="send-country" placeholder="country"></td>
                    <td class="px-2"><input type="text" class="form-control" id="send-city" placeholder="city"></td>
                    <td class="px-2"><input type="text" class="form-control" id="send-phone" placeholder="phone number"></td>  
                    <td class="px-2 text-center"><button type="button" class="btn btn-outline-primary border-0" id="btn-send-add"><i class='bx bxs-plus-square'></i></button></td>
                </form>
            </tr>
        `);

        $('#invitationType').select2({
            width: '100%',
            minimumResultsForSearch: Infinity
        });


        $('#btn-send-add').click(() => {
            const emailInput = $('#send-email');
            const nameInput = $('#send-name');
            const companyInput = $('#send-company');

            const type = $('#invitationType option:selected').val();
            const email = emailInput.val().trim();
            const name = nameInput.val();
            const company = companyInput.val();
            const jobTitle = $('#send-job-title').val();
            const country = $('#send-country').val();
            const city =  $('#send-city').val();
            const phone = $('#send-phone').val();

            if (isEmpty(email.trim())) {
                toastr.error('please enter the email.');
                emailInput.focus();
                return;
            }

            if(!isValidEmail(email)){
                toastr.error('invalid email format.');
                emailInput.focus();
                return;
            }

            if (isEmpty(name.trim())) {
                toastr.error('please enter the name.');
                nameInput.focus();
                return;
            }

            if (isEmpty(company.trim())) {
                toastr.error('please enter the company.');
                companyInput.focus();
                return;
            }


            const data = {
                type: type,
                email: email,
                name: name,
                company: company,
                jobTitle : isEmpty(jobTitle.trim()) ? null : jobTitle,
                country: isEmpty(country.trim()) ? null : country,
                city : isEmpty(city.trim()) ? null : city,
                phoneNumber :  isEmpty(phone.trim()) ? null : phone
            };

            sendDatatable.row.add(data).draw(false);

            $('#send-email').val('');
            $('#send-name').val('');
            $('#send-company').val('');
            $('#send-job-title').val('');
            $('#send-country').val('');
            $('#send-city').val('');
            $('#send-phone').val('');
            $('#invitationType').val('EXHIBITOR_BADGE').trigger('change');

        });
    }
}

