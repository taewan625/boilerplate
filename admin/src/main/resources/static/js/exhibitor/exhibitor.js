

window.addEventListener('load', () => {
    setSelect();
    //  setDateRangePicker();
    setDatatable();
    $("#btn-search").click(() => {
        datatable.ajax.reload();
    });


    $("#btn-registry").click(() =>{
        window.location.href = `/exhibitor/register`

    });


    $("#btn-remind").click(() => {
        if(confirm(`초청장 리마인드 메일을 발송하시겠습니까?`)){
            sendRemind();
        }else{
            toastr.info('',"발송을 취소하였습니다.",{timeOut:1500})
        }
    })


    $("#btn-invitation-excel").click(() => {
        getExcelDownload(`/api/v1/admin/exhibitor/invitation/excel/${$("#exhibition option:selected").val()}`);
    });

    $("#btn-excel").click(() => {
        getExcelDownload(`/api/v1/admin/exhibitor/excel/${$("#exhibition option:selected").val()}`);
    });
    loadingExit();

});
let datatable;

const sendRemind = () =>{
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/exhibitor/invitation/remind`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                toastr.success('',"정상적으로 발송되었습니다.",{timeOut:1500})
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
            url : `/api/v1/admin/exhibitor`,
            method : "POST",
            data : (d)=> {

                d.exhibitionId = $("#exhibition option:selected").val();
                d.applicationType= $("input[name=applicationType]:checked").val();
                d.sponsor= $("input[name=sponsor]:checked").val();
                d.industry= $("#industry option:selected").val();
                d.type= $("#search-type option:selected").val();
                d.searchText= $("#search-text").val();

            },
            beforeSend: () =>{loadingStart()} ,
            complete:() =>{loadingExit()},
            error: ()=>{}
        },
        processing: false,
        serverSide: true,
        order: [],
        columns: [
            { name : "NO", title : "No", data : "no", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    if(data === 1 && row.applicationDate === '9999-01-01'){
                        return `<i class="bx bxs-pin"></i>`;
                    }else{
                        return data;
                    }
                }
            },
            { name : "APPLICATION-TYPE", title : "TYPE", data : "applicationType", className : "dt-head-center dt-body-left px-2", orderable: false},
            {
                name: "SPONSOR", title : "SPONSOR", data : "sponsor", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    if(data){
                        return data;
                    }else{
                        return '-';
                    }
                }
            },
            { name : "COMPANY", title : "COMPANY", data : "company", className : "dt-head-center dt-body-left px-2", orderable: false,
                render:(data, type, row) =>{
                    let brand = '-';
                    if(row.brand){
                        brand = row.brand;
                    }

                    return `<span>${data}</span><br /><span class="text-muted">Brand: ${brand}</span>`
                }
            },
            { name : "COUNTRY", title : "COUNTRY", data : "country", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "BADGE", title : "BADGE", data : "invitation", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) => {
                    return  `<span class="text-muted">${data.sendBadgeCount}</span> / <span>${data.badgeCount}</span>`;
                }
            },
            { name : "INVITATION", title : "INVITATION", data : "invitation", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) => {
                    return  `<span class="text-muted">${data.sendInvitationCount}</span> / <span>${data.invitationCount}</span>`;
                }
            },
            { name : "APPLICATION-DATE", title : "APPLICATION DATE", data : "applicationDate", className : "dt-head-center dt-body-center px-2", orderable: false},

        ],
        "fnRowCallback": function(nRow, aData, iDisplayIndex) {
            $(nRow).click(function() {
                window.location.href = `/exhibitor/${aData.id}`
                //    goDetailUserInfo(aData.userId);
            });
        },
        columnDefs:[
            {targets:[0], width:"5%", padding:"0px"}
            ,{targets:[1], width:"10%", padding:"0px"}
            ,{targets:[2], width:"10%", padding:"0px"}
            ,{targets:[3], width:"30%", padding:"0px"}
            ,{targets:[4], width:"15%", padding:"0px"}
            ,{targets:[5], width:"10%", padding:"0px"}
            ,{targets:[6], width:"10%", padding:"0px"}
            ,{targets:[7], width:"10%", padding:"0px"}
        ],
    });
}



const setSelect = () => {
    $('#industry').select2({
        width: '100%',
        minimumResultsForSearch: Infinity
    });
    $('#search-type').select2({
        width: '100%',
        minimumResultsForSearch: Infinity
    });

}

