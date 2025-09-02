window.addEventListener('load', () => {
    const exhibitorId = getExhibitorId();
    getExhibitor(exhibitorId);
    setDatatable(exhibitorId);

    $("#btn-pre").click(() => {
        window.location.href = '/exhibitor';
    });

    $("#btn-update").click(() => {
        window.location.href = `/exhibitor/${exhibitorId}/modify`;
    });

    $("#btn-booth-update").click(() =>{
        updateBooth(exhibitorId);
    });

    $('#add-invitation-datatable tbody').on('click', '.btn-delete', function () {
        const rowData = datatable.row($(this).parents('tr')).data();
        const rowId = rowData.id;

        deleteAddInvitation(rowId);
    })

    $('#btn-delete').click(()=>{
        if(confirm("정말을 삭제하시겠습니까?")){
            deleteExhibitor(exhibitorId);
        }
    });


    loadingExit();
});
let datatable;
let badgeSendCount = 0;
let invitationSendCount = 0;



const deleteExhibitor = (id) => {
    $.ajax({
        type: "DELETE",
        url: `/api/v1/admin/exhibitor/${id}`,
        contentType: "application/json",
        dataType: "JSON",
        success: (res) => {
            if (res.success) {
                toastr.success('정상적으로 처리되었습니다.');
                window.location.href = `/exhibitor`;
            } else {
                toastr.error('실패 하였습니다.');
            }
        },
        beforeSend: () => { loadingStart() },
        complete: () => { loadingExit() },
        error: () => { toastr.error('서버 오류 발생'); }
    });
}



const getExhibitor = (exhibitorId) => {
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/exhibitor/${exhibitorId}`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success){

                Object.keys(res.data).forEach((key) => {
                    if (typeof res.data[key] === 'string') {
                        res.data[key] = unescapeHtml(res.data[key]);
                    }

                    switch (key) {
                        case 'invitation':
                            const invitation = res.data[key];
                            badgeSendCount = invitation.sendBadgeCount;
                            invitationSendCount = invitation.sendInvitationCount;

                            $('#badgeCount').val(invitation.badgeCount);
                            $('#invitationCount').val(invitation.invitationCount);
                            $('#sendBadgeCount').val(badgeSendCount);
                            $('#sendInvitationCount').val(invitationSendCount);

                            break;
                        case 'scaMembership':
                            if(res.data[key]){
                                $('#sca').prop('checked', true);
                            }
                            break;
                        case 'scaiSales':
                            if(res.data[key]){
                                $('#scai').prop('checked', true);
                            }
                            break;
                        case 'mutualTaxExemption':
                            if(res.data[key]){
                                $(`#country-badge`).html(`<span class="badge bg-label-success ">Mutual Tax Exemption Country</span>`);
                            }
                            break;
                        case 'homepage':
                        case 'instagram':
                        case 'facebook':
                        case 'etcSns':
                            const content = `${res.data[key]} ${getUrl(res.data[key])}`;
                            if(content.startsWith('null') || !content.trim()){
                                $(`#${key}`).html('-');
                            }else{
                                $(`#${key}`).html(content);
                            }
                            break;

                        default:
                            $(`#${key}`).val(res.data[key]);
                    }
                });
            }
        },
        beforeSend: () =>{loadingStart()} ,
        complete:() =>{loadingExit()},
        error: (jqXHR, textStatus, errorThrown) => {},
        async: false
    });
}

const updateBooth = (id) =>{
    const boothCount = $("#boothCount").val();
    const boothNumber = isEmpty($("#boothNumber").val().trim()) ? null : $("#boothNumber").val();
    if (!boothCount || boothCount < 0) {
        toastr.error('please enter the count.');
        $('#boothCount').focus();
        return;
    }



    const data = {
        boothCount:boothCount,
        boothNumber:boothNumber
    }

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/exhibitor/booth/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: "JSON",
        success: (res) => {
            if (res.success) {
                toastr.success('정상적으로 처리되었습니다.');
                getExhibitor(id);
                $("#file").val("");
                sendDatatable.clear().draw(false);
            } else {
                toastr.error('실패 하였습니다.');
            }
        },
        beforeSend: () => { loadingStart() },
        complete: () => { loadingExit() },
        error: () => { toastr.error('서버 오류 발생'); }
    });
}


const getUrl = (url) => {
    if (!url) {
        return '';
    }

    if (!url.startsWith('http://') && !url.startsWith('https://')) {
        return  `<a class="btn-outline-primary border-0" href="https://${url}" target="_blank" rel="noopener noreferrer"><i class='bx bx-link-external'></i> </a>`;
    }


    return `<a class="btn-outline-primary border-0" href="${url}" target="_blank" rel="noopener noreferrer"><i class='bx bx-link-external'></i> </a>`;
}


const getExhibitorId = () => {
    const path = window.location.pathname;
    const match = path.match(/\/exhibitor\/(\d+)/);
    const id = match ? match[1] : null;
    return id;
}



const setDatatable = (id) => {
    datatable = $("#add-invitation-datatable").DataTable({
        searching: false,
        info: false,   //하단 페이지 수 비활성화
        infoCallback: (settings, start, end, max, total, pre) =>{
            return `Total ${max}`;
        },//  엔트리 갯수 custom
        lengthChange: false, // 상단 엔트리 개수 설정 비활성화
        language: {   //로딩 중 문자 수정
            processing :""
        },
        paging: false,
        bPaginate: false,
        responsive: true,
        ajax: {
            url : `/api/v1/admin/exhibitor/add-invitation/${id}`,
            method : "POST",
            data : (d)=> {},
            beforeSend: () =>{loadingStart()} ,
            complete:(d) =>{loadingExit()},
            error: ()=>{}
        },
        processing: false,
        serverSide: false,
        order: [],
        columns: [
            { name : "BADGE-COUNT", title : "BADGE COUNT", data : "badgeAddCount", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "INVITATION-COUNT", title : "INVITATION COUNT", data : "invitationAddCount", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "MEMO", title : "memo", data : "reason", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "ADD-DATE", title : "ADD DATE", data : "createdAt", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "ACTION", title : "", data : "id", className : "dt-head-center dt-body-center px-2", orderable: false,
                render: (data, type, row) =>{
                    return `<button type="button" class="btn btn-outline-danger border-0 btn-delete"><i class='bx bxs-minus-square'></i></button>`
                }
            },

        ],
        fnRowCallback: (nRow, aData, iDisplayIndex)=> {},
        columnDefs:[
            {targets:[0], width:"15%", padding:"0px"}
            ,{targets:[1], width:"15%", padding:"0px"}
            ,{targets:[2], width:"50%", padding:"0px"}
            ,{targets:[3], width:"15%", padding:"0px"}
            ,{targets:[4], width:"5%", padding:"0px"}

        ],
        initComplete: () =>{
            appendBoothInputRow(id);
        },
        drawCallback: () =>{
            appendBoothInputRow(id);
        }
    });
}

const appendBoothInputRow = (id) => {
    // 이미 있으면 다시 안 붙이도록 체크
    if ($('#input-row').length === 0) {
        $('#add-invitation-datatable tbody').prepend(`
            <tr id="input-row">
                <form id="input-row-form" onsubmit="return false;">
                    <td class="px-2"><input type="number" min="0" class="form-control bg-white text-center" id="addBadgeCount" placeholder="add count"></td>
                    <td class="px-2"><input type="number" min="0" class="form-control bg-white text-center" id="addInvitationCount" placeholder="add count"></td>
                    <td class="px-2"><input type="text" class="form-control" name="addDescription" id="addDescription" placeholder="memo"></td>
                    <td class="px-2 text-center">-</td>
                    <td class="px-2 text-center"><button type="button" class="btn btn-outline-primary border-0" id="btn-add"><i class='bx bxs-plus-square'></i></button></td>
                </form>
            </tr>
        `);

        $('#boothType').select2({
            width: '100%',
            minimumResultsForSearch: Infinity
        });

        $('#btn-add').click(() => {

            const addBadgeCount = $('#addBadgeCount').val();
            const addInvitationCount = $('#addInvitationCount').val();
            const addDescription = $('#addDescription').val().trim();


            if (!addBadgeCount) {
                toastr.error('please enter the count.');
                $('#addBadgeCount').focus();
                return;
            }
            if (!addInvitationCount) {
                toastr.error('please enter the count.');
                $('#addInvitationCount').focus();
                return;
            }

            if(!((addInvitationCount+addBadgeCount) > 0)){
                toastr.error('please enter the count.');
                $('#addBadgeCount').focus();
                return;
            }

            if (!addDescription) {
                toastr.error('please enter the description.');
                $('#addDescription').focus();
                return;
            }


            const data = {
                exhibitorId: id,
                badgeAddCount: addBadgeCount,
                invitationAddCount: addInvitationCount,
                reason: addDescription,
            };

            $.ajax({
                type: "POST",
                url: `/api/v1/admin/exhibitor/add-invitation`,
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "JSON",
                success: (res) => {
                    if (res.success) {
                        toastr.success('정상적으로 처리되었습니다.');
                        getExhibitor(id);
                        $("#file").val("");
                        sendDatatable.clear().draw(false);
                        datatable.ajax.reload(null, false);
                    } else {
                        toastr.error('실패 하였습니다.');
                    }
                },
                beforeSend: () => { loadingStart() },
                complete: () => { loadingExit() },
                error: () => { toastr.error('서버 오류 발생'); }
            });
        });
    }
}


const deleteAddInvitation = (id) => {
    $.ajax({
        type: "DELETE",
        url: `/api/v1/admin/exhibitor/add-invitation/${id}`,
        contentType: "application/json",
        dataType: "JSON",
        success: (res) => {
            if (res.success) {
                toastr.success('정상적으로 처리되었습니다.');
                getExhibitor(getExhibitorId());
                $("#file").val("");
                sendDatatable.clear().draw(false);
                datatable.ajax.reload(null, false);
            } else {
                toastr.error('실패 하였습니다.');
            }
        },
        beforeSend: () => { loadingStart() },
        complete: () => { loadingExit() },
        error: () => { toastr.error('서버 오류 발생'); }
    });
}