//TODO 개선: 추후 일괄적으로 이미지를 서버에서 내려받아서 관리할 수 있도록 동적처리 필요 (예: 이미지를 내리는 core service에서 받을 수 있도록 처리)
const tempImgList = [
    { path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/SCA/240502_WCB_09.%20SCA%20Lecture%20Series_CHN-0172.webp',},
    { path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/SCA/240502_WCB_09.%20SCA%20Lecture%20Series_CHN-0503.webp',},
    { path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/SCA/240502_WCB_09.%20SCA%20Lecture%20Series_CHN-1635.webp',},
];

//TODO 개선: 추후 일괄적으로 이미지를 서버에서 내려받아서 관리할 수 있도록 동적처리 필요 (예: 이미지를 내리는 core service에서 받을 수 있도록 처리)
const schedule = {
    day1: [
        {
            label: `Session 1`,
            time: `11:00 ~ 12:00`,
            title: `Introduction to the SCA Coffee Value Assessment`,
            speaker: [{name: 'Pamela Chng', description: `CEO & Co-Founder of Bettr Academy`}],
            image: [{path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series-day1/Photo-PamelaChng-bettr-2023-sm.webp'},],
        },
        {
            label: `Session 2`,
            time: `12:00 ~ 13:00`,
            title: `CVA in SCA Education & Competition`,
            speaker: [{
                name: 'Stephen Morrissey',
                description: `Chief Commercial Officer of Specialty Coffee Association`
            }],
            image: [{path:'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series-day1/Stephen%20Morrisey.webp'}],
        },
        {
            label: `Session 3`,
            time: `13:00 ~ 14:00`,
            title: `Why CVA for Coffee Trainers`,
            speaker: [{name: 'Adi W. Taroepratjeka', description: `Director of 5758 Coffee Lab`}],
            image: [{path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series-day1/Adi%20W.%20Taroepratjeka.webp'}],
        },
        {
            label: `Session 4`,
            time: `14:00 ~ 15:00`,
            title: `Brewing Knowledge: Research, Engagement, and Learning at the SCA and the Coffee Science Foundation`,
            speaker: [{name: 'Laurel Carmichael', description: `Publications Manager of Specialty Coffee Association`}],
            image: [{path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series-day1/Laurel%20Carmichael%20image.webp'}],
        },
        {
            label: `Session 5`,
            time: `15:00 ~ 16:00`,
            title: `Bridging the Gap: Achieving Living-Prosperous Income and Equitable Value Distribution Across Coffee Industry in Indonesia`,
            speaker: [
                {name: 'Nonie Kaban', description: `Board Member of SCOPI, Head of Program Rikolto Indonesia`},
                {
                    name: 'Christina ARCHER',
                    description: `Strategic Adviser for Livelihoods, Sustainable Food Lab – Task Force Work Stream`
                }
            ],
            image: [
                {path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series-day1/Nonie%20photo.jpg',},
                {path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series-day1/Christina%20Archer.jpg',},
            ],
        },
        {
            label: `Session 6`,
            time: `16:00 ~ 17:00`,
            title: `Promoting Sustainable Consumption and Production in Coffee`,
            speaker: [
                {
                    name: 'Irvan Helmi',
                    description: `Chairman of the SCOPI Executive Board, Co-Founder of Anomali Coffee`
                },
                {
                    name: 'Nina Rossiana',
                    description: `Corporate Engagement and Partnership Manager at Rainforest Alliance, SCOPI Member`
                },
                {name: 'Andanu Prasetyo', description: `Founder of Toko Kopi Tuku, SCOPI Member`}
            ],
            image: [
                {path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series-day1/thumbnail_Irvan%20Helmi.jpg',},
                {path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series-day1/2.jpg',},
                {path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series-day1/1.jpg',},
            ],
        },
    ],

    day2: [
        {
            label: 'Session 1',
            time: '10:30 ~ 11:20',
            title: 'Fostering Resilient Coffee Commodities through Data and Technology Driven in Indonesia',
            speaker: [{
                name: 'Dini Astika',
                description: 'Head of ICCRI (Indonesian Coffee and Cocoa Research Institute)\nExecutive Vice President& Molecular Biology Researcher'
            }],
            image: [
                {path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series/Dini_Photograph%20%EB%B3%B5%EC%82%AC.webp'},
            ],
        },
        {
            label: 'Session 2',
            time: '11:20 ~ 12:10',
            title: 'Roasting is Fun',
            speaker: [{
                name: 'Steve Ganiputra Hidayat',
                description: 'Chairman of PaPIKI (Perkumpulan Professional Inovator Kopi Indonesia)'
            }],
            image: [
                {path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series/IMG-20241121-WA0003.webp'},
            ],
        },
        {
            label: 'Lunch',
            time: '12:10 ~ 13:00',
            title: 'Lunch Break',
            speaker: [],
            image: [],
        },
        {
            label: 'Session 3',
            time: '13:00 ~ 13:50',
            title: 'Are We Ready?\nWhen Coffee Couture Demands Sustainability, Who Pays the Price?',
            speaker: [{
                name: 'Veronica Saka Dala',
                description: 'Co-Founder & Director of Saka Dala'
            }],
            image: [
                {path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series/Photo%20Veronica%20%28colour%29.webp'},
            ],
        },
        {
            label: 'Session 4',
            time: '13:50 ~ 14:40',
            title: 'Our Commitment to Secure the Future of Coffee for All,from Farmers to Consumers',
            speaker: [{
                name: 'Roberto Alonso Vega Alfaro',
                description: 'Vice President of Global Agronomy R&D Sustainability at The Starbucks Coffee Company'
            }],
            image: [
                {path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series/Roberto%20WOC%20Jakarta%20Connect_v2.webp'},
            ],
        },
        {
            label: 'Session 5',
            time: '14:40 ~ 15:30',
            title: 'Implementing Microbial Starters in Coffee Production in Indonesia',
            speaker: [
                {
                    name: 'Mikael Jasin',
                    description: 'Champion of World Barista Championship 2024 CEO of Catur Coffee'
                },
                {
                    name: 'Intan Taufik',
                    description: 'Researcher at the School of Life Sciences & Technology, Institut Teknologi Bandung'
                }
            ],
            image: [
                {path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series/MIKAEL-JASIN-.jpg',},
                {path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series/Intan%20Taufik%20image.jpg'},
            ],
        },
        {
            label: 'Session 6',
            time: '15:30 ~ 16:20',
            title: 'Farmer Business School & Regenerative Agriculture',
            speaker: [{
                name: 'Danny Ferry Juddin',
                description: 'Senior Advisor of GIZ'
            }],
            image: [
                {path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series/DFJ%2001.webp'},
            ],
        },
        {
            label: 'Session 7',
            time: '16:20 ~ 17:10',
            title: 'Back to our Roots',
            speaker: [{
                name: 'Eko Purnomo Widi',
                description: 'Co-Founder of Klasik Beans Cooperative'
            }],
            image: [
                {path: 'https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/sca-lecture-series/EKO%20PURNOMOWIDI%20%EB%B3%B5%EC%82%AC.webp'},
            ],
        },
    ],
};

//모듈
const scaLectureSeriesModule = (() => {
    //TODO 개선: 추후 일괄적으로 이미지를 서버에서 내려받아서 관리할 수 있도록 동적처리 필요 (예: 이미지를 내리는 core service에서 받을 수 있도록 처리) - ajax 생성

    //스케줄 그리는 모듈
    const renderSchedule = (data, options) => {
        //날짜별 스케줄 목록 반복
        Object.keys(data).forEach((day, index) => {
            const schedules =  data[day];

            //tab content 영역 추가
            $('#scheduleTabContents').append(`<div class="tab-pane outline-none fade show ${(index === 0) ? 'active' : ''}" id="${day}" role="tabpanel" aria-labelledby="${day}-tab" tabindex="0">
                                          <div class="row gy-4 component__desc-font--middle fw-semibold" id="${day}Section">
                                          </div>
                                      </div>`);

            //스케줄 목록 반복
            schedules.forEach((schedule, index) => {
                //연사 정보 목록 그리기
                let speakerHTML = '';
                schedule.speaker.forEach((data, key) => {
                    speakerHTML += `<div class="pt-3">
                                        <span>${data.name}</span>
                                        <br>
                                        <span class="component__desc-font--small fw-normal">${data.description}</span>
                                      </div>`;
                });

                //연사 이미지 표출 영역 그리기
                const speakerImgSectionHTML = (speakerHTML) ?
                                            `<section class="pb-5 pb-md-0" id="${day}SpeakerSection${index}">
                                                <div id="${day}Speaker${index}" class="carousel slide" data-bs-ride="carousel">
                                                  <div class="carousel-indicators"></div>
                                                  <div class="carousel-inner overflow-hidden" style="height: 180px; width: 240px;"></div>
                                                </div>
                                              </section>`
                                            : '';

                //단건 스케줄 영역 그리기
                const scheduleHTML = `<div class="col-12">
                                             <div class="row g-3">
                                                <div class="col-12 col-md-2">
                                                  <div class="h-100 bg-light d-flex flex-column justify-content-center text-center p-3">
                                                    <span>${schedule.label}</span>
                                                    <span>${schedule.time}</span>
                                                  </div>
                                                </div>
                                                <div class="col-12 col-md-10">
                                                  <div class="d-flex flex-column-reverse flex-md-row justify-content-between align-items-center h-100 bg-light">
                                                    <div class="p-3 w-100">
                                                      <p class="mb-0">${schedule.title}</p>
                                                      ${speakerHTML}
                                                    </div>
                                                    ${speakerImgSectionHTML}
                                                  </div>
                                                </div>
                                            </div>
                                           </div>`;

                $('#'+day+'Section').append(scheduleHTML);

                //슬라이드 이미지 모듈 사용하기
                renderCarouse(schedule.image, {id: `${day}Speaker${index}`, imgClass: 'd-block object-fit-cover w-100'});
            })
        });
    };

    return {renderSchedule};
})();


//모듈 초기화
(() => {
    renderCarouse(tempImgList, {id: "carouselIndicators", imgClass: ''});
    scaLectureSeriesModule.renderSchedule(schedule);
})();

