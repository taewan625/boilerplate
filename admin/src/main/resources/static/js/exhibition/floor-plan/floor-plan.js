window.addEventListener('load', () => {
    setDatatable();

    $('#registry-modal').on('show.bs.modal', (e)=> {
//        $('#modal-exhibition-name').text($('#exhibition option:selected').text());
        $('#file').val('');
    });

    $("#file").change((e)=>{
        fileChange(e);
    });

    $('#btn-modal-registry').click(()=>{
        if(!($('#file')[0].files[0])){
            toastr.error('Floor Plan파일을 선택해주세요.');
            return;
        }
        insertFloorPlan();
    })

    $('#btn-publish').click(()=>{
        if(confirm('현재 배치도를 게시 하시겠습니까?')){
            updateFloorPlan();
        }
    });

    loadingExit();
});

let datatable;

const fileChange = (e) => {
    const file = e.target.files[0];
    const maxSize = 10 * 1024 * 1024;

    if(!file){
        return;
    }

    // 파일 타입 체크 (png)
    if (!file.type.startsWith('application/pdf')) {
        toastr.error('PDF 파일만 선택해주세요.');
        e.target.value = ''; // 선택된 파일 초기화
        return;
    }

    if (file.size > maxSize) {
        toastr.error('파일 크기는 10MB 이하만 가능합니다.');
        e.target.value = ''; // 선택된 파일 초기화
    }
}

const updateFloorPlan = () =>{
    const id = $('#floorPlanId').val();
    const data = {
      exhibitionId : $('#exhibition option:selected').val()
    };

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/floor-plan/${id}`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                toastr.success('',"정상적으로 처리되었습니다.",{timeOut:1500})
                datatable.ajax.reload();
            }else{
                toastr.error('',"실패 하였습니다.",{timeOut:1500})
            }
        },
        beforeSend: () =>{loadingStart()} ,
        complete:() =>{loadingExit()},
        error: (jqXHR, textStatus, errorThrown) => {},
        async: false
    });
}

const geFormData = () =>{
    const formData = new FormData();

    formData.append('exhibitionId', $('#exhibition option:selected').val());

    if($('#file')[0].files[0]){
        formData.append('file', $('#file')[0].files[0]);
    }

    return formData;
}

const insertFloorPlan = () =>{
    const formData = geFormData();
    $.ajax({
        type: "POST",
        url: `/api/v1/admin/floor-plan/register`,
        data: formData,
        enctype:'multipart/form-data',
        contentType: false,
        processData: false,
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            const modalEl = document.getElementById('registry-modal');
            const modalInstance = bootstrap.Modal.getInstance(modalEl);

            if(res.success){
                toastr.success('',"저장 되었습니다.",{timeOut:1500})
                datatable.ajax.reload();
                modalInstance.hide();
            }else{
                if(res.message === "Invalid MIME type detected"){
                    toastr.error('',"변조된 파일은 업로드할 수 없습니다.",{timeOut:1500})
                }else{
                    toastr.error('',"실패 하였습니다.",{timeOut:1500})
                }
            }
        },
        beforeSend: () =>{loadingStart()} ,
        complete:() =>{loadingExit()},
        error: (jqXHR, textStatus, errorThrown) => {},
        async: false
    });

}

const setDatatable = () => {
    datatable = $("#datatable").DataTable({
        searching: false,
        pageLength: 10,
        info: true,   //하단 페이지 수 비활성화
        infoCallback: (settings, start, end, max, total, pre) =>{
            return `Total ${max}`;
        },//  엔트리 갯수 custom
        lengthChange: false, // 상단 엔트리 개수 설정 비활성화
        language: {   //로딩 중 문자 수정
            processing :""
        },
        paging: true,
        bPaginate: true,
        responsive: true,
        ajax: {
            url : `/api/v1/admin/floor-plan`,
            method : "POST",
            data : (d)=> {
                d.exhibitionId = $("#exhibition option:selected").val();
            },
            beforeSend: () =>{loadingStart()} ,
            complete:(d) =>{
                const data = d.responseJSON.data;
                const result = data.find(item => item.deleted == 0)
                setViewer(result);
                loadingExit()
            },
            error: ()=>{}
        },
        processing: false,
        serverSide: true,
        order: [],
        columns: [
            { name : "NO", title : "No", data : "no", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                        if(!row.deleted){
                            return `<i class='bx bxs-pin'></i>`;
                        }else{
                            return data;
                        }
                }
            },
            { name : "FILENAME", title : "FILE NAME", data : 'filename', className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "STATUS", title : "STATUS", data : "deleted", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    if(data == 0){
                        return `<span class="badge bg-label-success">PUBLISHED</span>`
                    }
                    if(data == 1){
                        return `<span class="badge bg-label-danger ">UNPUBLISHED</span>`
                    }

                }
            },
            { name : "DATE", title : "DATE", data : "createdAt", className : "dt-head-center dt-body-center px-2", orderable: false},

        ],
        "fnRowCallback": function(nRow, aData, iDisplayIndex) {
            $(nRow).on('click', ()=> {
                // 모든 행의 활성화 제거
                $(datatable.rows().nodes()).removeClass('row-selected');
                // 현재 클릭한 행에 활성화 클래스 추가
                $(nRow).addClass('row-selected');

                setViewer(aData);
                //    goDetailUserInfo(aData.userId);
            });
        },
        columnDefs:[
            {targets:[0], width:"10%", padding:"0px"}
            ,{targets:[1], width:"55%", padding:"0px"}
            ,{targets:[2], width:"15%", padding:"0px"}
            ,{targets:[3], width:"20%", padding:"0px"}
        ],
    });
}

const setViewer = (data) =>{
    $('#no').val(data.no);
    $('#filename').val(data.filename);

    if(data.deleted == 0){
        $('#btn-publish').attr('disabled', 'disabled');
        $('#status').val('PUBLISHED');
    }

    if(data.deleted == 1){
        $('#btn-publish').removeAttr('disabled');
        $('#status').val('UNPUBLISHED');
    }

    $('#date').val(data.createdAt);
    $('#viewer').attr('src', data.filePath);
    $('#floorPlanId').val(data.id);
}
