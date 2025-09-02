window.addEventListener('load', () => {
    setDateRangePicker();
    getDashboardData(false);

    $("#date-range").change(()=> {
        $('.dashboard-date').text(`(${$("#date-range").val()})`)
        getDashboardData(true);
    })

    $("#btn-reset").click(() => {
        const datePicker = $("#date-range").data("daterangepicker");
        datePicker.setStartDate(moment().subtract(14, 'days').format('YYYY-MM-DD'));
        datePicker.setEndDate(moment().format('YYYY-MM-DD'));

        $("#date-range").trigger("change");
    });

    loadingExit();
});

let salesChart;
let pvChart;

const getDashboardData = (refresh) => {
    const exhibitionId =$('#exhibition option:selected').val();
    let datepicker = $("#date-range").data("daterangepicker");
    const data = {
        startDate: datepicker.startDate.format("YYYY-MM-DD"),
        endDate:datepicker.endDate.format("YYYY-MM-DD")
    }

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/dashboard/${exhibitionId}`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        beforeSend: () => {
            setTimeout(() => {
                loadingStart();
            }, 100);
        },
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                $('.dashboard-date').text(`(${$("#date-range").val()})`)
                if(refresh){
                    const seriesData = getSalesChartData(res.data.weekSales);

                    salesChart.updateOptions({
                        xaxis: {
                            categories: seriesData.labels,
                            formatter: function(value){
                               return moment(value).format("MM/DD");
                            }
                        },
                        chart: {
                            animations: {
                                enabled: true,
                                easing: 'easeinout',
                                speed: 800,
                                animateGradually: {
                                    enabled: true,
                                    delay: 150
                                },
                                dynamicAnimation: {
                                    enabled: true,
                                    speed: 800
                                }
                            }
                        }
                    });
                    salesChart.updateSeries(seriesData.series, true);

                    const pvSeries =[{data: res.data.visitTotals.map(item => item.totalVisitCount)}] ;
                    const pvLabels = res.data.visitTotals.map(item => item.groupedReferer);

                    pvChart.updateOptions({
                        xaxis: {
                            categories: pvLabels
                        },
                        chart: {
                            animations: {
                                enabled: true,
                                easing: 'easeinout',
                                speed: 800,
                                animateGradually: {
                                    enabled: true,
                                    delay: 150
                                },
                                dynamicAnimation: {
                                    enabled: true,
                                    speed: 800
                                }
                            }
                        }
                    });
                    pvChart.updateSeries(pvSeries, true);

                    const totalSum = res.data.visitTotals.reduce((sum, item) => sum + item.totalVisitCount, 0);
                    $('#total-visitor').text(numberWithCommas(totalSum));
                }else{
                    setSalesChart(res.data.weekSales);
                    setPvChart(res.data.visitTotals);
                }

                setTotalSales(res.data.totalSales);
                setDataRangeSales(res.data.dateRangeSales);
            }else{
                toastr.error('',"Data Not Load",{timeOut:1500})
            }
        },
        complete: () => {
            setTimeout(() => {
                loadingExit();
            }, 100);
        },
        error:(jqXHR, textStatus, errorThrown) => {},
        async: false
    });
}

const getSalesChartData = (weekSales) => {
    // 1. 날짜 리스트 (X축 Labels)
    const labels = [...new Set(weekSales.map(item => item.saleDate))];

    // 2. 중복 제거한 티켓 이름 리스트
    const ticketNames = [...new Set(weekSales.map(item => item.ticketName))];

    // 3. 티켓별 totalQuantity 데이터셋 생성
    const quantitySeries = ticketNames.map(ticket => {
        return {
            name: ticket, // 티켓 이름을 시리즈명으로 설정
            type: 'line',
            yaxisIndex: 0,
            data: labels.map(date => {
                // 해당 날짜와 티켓 이름에 해당하는 모든 데이터를 필터링
                const sales = weekSales.filter(s => s.saleDate === date && s.ticketName === ticket);
                // totalQuantity 값을 합산
                return sales.reduce((sum, sale) => sum + (isFinite(sale.totalQuantity) ? sale.totalQuantity : 0), 0);
            })
        };
    });

    // 4. 날짜별 totalSales 합산
    const totalSalesData = labels.map(date => {
        return weekSales
            .filter(sale => sale.saleDate === date)
            .reduce((sum, sale) => sum + (isFinite(sale.totalSales) ? sale.totalSales : 0), 0);
    });

    // 5. totalSales 데이터를 ApexCharts 시리즈에 추가
    const totalSalesSeries = {
        name: "Total Amount (USD)", // 총 매출 데이터셋
        type: 'column', // 바 그래프로 표시
        yaxisIndex: 2,
        data: totalSalesData.map(value => isFinite(value) ? value : 0) // NaN 방지
    };

    // 6. 최종 차트 데이터 구조
    const chartData = {
        series: [...quantitySeries, totalSalesSeries], // 티켓별 판매량 + 총 매출
        labels: labels
    };

    return chartData;
};


const setSalesChart = (weekSales) => {
    // 6. 최종 차트 데이터 구조
    const chartData =  getSalesChartData(weekSales);
    const options = {
        series: chartData.series,
        chart: {
            height: 570,
            type: 'line',
            toolbar: { show: false },
            zoom: { enabled:false },
            animations: {
                enabled: true,
                easing: 'easeinout',
                speed: 800,
                animateGradually: {
                    enabled: true,
                    delay: 150
                },
                dynamicAnimation: {
                    enabled: true,
                    speed: 800
                }
            }
        },
        colors: ["#FF8989", "#CB9DF0", "#9694FF"],
        stroke: {
            width: [2, 2, 2, 0],
            curve: 'smooth'
        },
        plotOptions: {
            bar: {
                borderRadius: 3,
                borderRadiusApplication: 'end',
                borderRadiusWhenStacked: 'all'
            }
        },
        legend: {
            show: false,
        },
        dataLabels: { enabled: true, enabledOnSeries: [0, 1]},
        xaxis: {
            categories: chartData.labels,
            tooltip: { enabled: false },
            labels: {
                formatter: function (value) {
                    return moment(value).format("MM/DD");
                }
            }
        },
        yaxis: [
            {
                seriesName: ['1-day badge', '3-days badge'],
                min:0,
                show: false,
                opposite: false,
                title: { text: 'Quantity' },
                legend: {
                    show: false,
                },
            },
            {
                seriesName: 'Total Amount (USD)',
                min:0,
                opposite: true,
                show: false,
                title: { text: 'Total Amount (USD)' }
            }
        ],
        tooltip: {
            x: {
                show: true,
            },
            y: {
                formatter: function (value) {
                    return isFinite(value) ? numberWithCommas(Math.round(value)) : "0";
                }
            }
        }
    };

    salesChart = new ApexCharts(document.querySelector("#salesCharts"), options);
    salesChart.render().then(() => {
        createCustomLegend(salesChart, chartData.series); // ✅ 차트 생성 후 범례 동적 추가
    });
};

function createCustomLegend(chart, seriesData) {
    const legendContainer = document.getElementById("custom-legend");
    legendContainer.innerHTML = ""; // 기존 범례 초기화

    seriesData.forEach((serie, index) => {
        const legendItem = document.createElement("div");
        legendItem.style.display = "flex";
        legendItem.style.alignItems = "center";
        legendItem.style.cursor = "pointer";

        // 색상 마커
        const colorBox = document.createElement("span");
        colorBox.style.width = "12px";
        colorBox.style.height = "12px";
        colorBox.style.borderRadius = "50%";
        colorBox.style.backgroundColor = chart.w.globals.colors[index]; // ApexCharts 색상 사용
        colorBox.style.marginRight = "5px";

        // 범례 텍스트
        const labelText = document.createElement("span");
        labelText.textContent = serie.name;
        labelText.style.fontSize = "14px";
        labelText.style.color = "#333";

        legendItem.appendChild(colorBox);
        legendItem.appendChild(labelText);
        legendContainer.appendChild(legendItem);

        // 범례 클릭 이벤트 (토글 기능)
        legendItem.addEventListener("click", function () {
            const isHidden = chart.w.globals.collapsedSeriesIndices.includes(index);
            if (isHidden) {
                chart.toggleSeries(serie.name);
                legendItem.style.opacity = "1";
            } else {
                chart.toggleSeries(serie.name);
                legendItem.style.opacity = "0.5"; // 비활성화 시 흐리게
            }
        });
    });
}

const setPvChart= (totalVisit) =>{
    const data = totalVisit.map(item => item.totalVisitCount);
    const labels = totalVisit.map(item => item.groupedReferer);
    const options = {
        series: [{
            data: data
        }],
        chart: {
            type: 'bar',
            height: 450,
            toolbar: {
                show: false
            },
            animations: {
                enabled: true,
                easing: 'easeinout',
                speed: 800,
                animateGradually: {
                    enabled: true,
                    delay: 150
                },
                dynamicAnimation: {
                    enabled: true,
                    speed: 800
                }
            }
        },
        colors:['#9694FF'],
        annotations: {
            xaxis: [],
            yaxis: []
        },
        plotOptions: {
            bar: {
                horizontal: true,
                dataLabels: {
                    position: 'bottom'
                },
                borderRadius: 3,
                borderRadiusApplication: 'end',
                borderRadiusWhenStacked: 'all'
            }
        },
        dataLabels: {
            enabled: true,
            textAnchor: 'start',
            offsetX: 0,
            style: {
                colors: ['#000000']
            },
            formatter: function (val, opt) {
                return `${opt.w.globals.labels[opt.dataPointIndex]}: ${val}`;
            },
            dropShadow: {
                enabled: false
            }
        },
        xaxis: {
            categories: labels
        },
        grid: {
            xaxis: {
                lines: {
                    show: true
                }
            }
        },
        yaxis: {
            reversed: false,
            labels: {
                show: false
            },
            min:0,
            forceNiceScale: true

        },
        tooltip: {
            theme: 'dark',
            x: {
                show: false
            },
            y: {
                title: {
                    formatter: function () {
                        return ''
                    }
                }
            }
        }
    };

    pvChart = new ApexCharts(document.querySelector("#totalPvChart"), options);
    pvChart.render();

    const totalSum = totalVisit.reduce((sum, item) => sum + item.totalVisitCount, 0);
    $('#total-visitor').text(numberWithCommas(totalSum));
}

const setTotalSales = (sales) => {
    if (!sales || sales.length < 1) return;

    const groupedByType = sales.reduce((acc, item) => {
        if (!acc[item.ticketType]) {
            acc[item.ticketType] = [];
        }

        acc[item.ticketType].push(item);

        return acc;
    }, {});

    let total = 0;
    let html = '';
    Object.keys(groupedByType).forEach(type => {
        const categoryClass = type === "RGLR" ? "sales-category mt-3" : "sales-category";
        const titleName = type === "ELBD" ? "Early Bird" : "Regular";

        html += `<div class="${categoryClass}">${titleName}</div><div>`;
        groupedByType[type].forEach(item => {
            html += `
                <div class="sales-item">
                    <div>
                        <h6>${item.ticketName}</h6>
                        <small>Quantity: ${numberWithCommas(item.totalQuantity)}</small>
                    </div>
                    <div class="sales-amount">${numberWithCommas(item.totalSales)} USD</div>
                </div>`;

            total += item.totalSales;
        });

        html += `</div>`;
    });

    html += `
        <div class="sales-item border-top">
            <div>
                <span class="fs-5 fw-bold">Total Amount</h6>
            </div>
            <div class="fs-5 fw-bold sales-amount-total">${numberWithCommas(total)} USD</div>
        </div>
    `

    $('#total-sales').html(html);
};

const setDataRangeSales = (sales) => {
    if (!sales || sales.length < 1) return;

    const aggregatedData = sales.reduce((acc, item) => {
        if (!acc[item.ticketName]) {
            acc[item.ticketName] = {
                ticketName: item.ticketName,
                totalQuantity: 0,
                totalSales: 0
            };
        }

        acc[item.ticketName].totalQuantity += item.totalQuantity;
        acc[item.ticketName].totalSales += item.totalSales;

        return acc;
    }, {});

// 객체를 배열로 변환
    const result = Object.values(aggregatedData).sort((a, b) => a.ticketName.localeCompare(b.ticketName));

    let html = '<div>';
    result.forEach(item => {
        html += `
            <div class="sales-item">
                <div>
                    <h6>${item.ticketName}</h6>
                    <small>Quantity: ${numberWithCommas(item.totalQuantity)}</small>
                </div>
                <div class="sales-amount">${numberWithCommas(item.totalSales)} USD</div>
            </div>`;
    });

    html += '</div>';
    $('#sales-by-period').html(html);
};

const setDateRangePicker = () =>{
    $("#date-range").daterangepicker({
        locale: {
            format: "YYYY-MM-DD",
            separator: " ~ ",
            applyLabel: "확인",
            cancelLabel: "취소",
            fromLabel: "From",
            toLabel: "To",
            customRangeLabel: "Custom",
            weekLabel: "W",
            daysOfWeek: ["일", "월", "화", "수", "목", "금", "토"],
            monthNames: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
        },
        timePicker: false,
        startDate: moment().subtract(14,'days').format('YYYY-MM-DD'),
        endDate: moment().format('YYYY-MM-DD')
    })
}
