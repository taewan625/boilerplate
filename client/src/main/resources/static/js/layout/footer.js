window.addEventListener("load", () => {
    //quick 이동 경로 === 현재 경로
    if (window.location.pathname === $('#quickPath').attr('href')) {
        $('#quickBtn').addClass('d-none');
        $('#topButton').addClass('opacity-0');

        //윈도우 scroll 이벤트
        $(window).off('scroll').on('scroll', () => {
            $('#topButton').toggleClass('opacity-0 invisible', (window.scrollY <= 200));
            $('#topButton').toggleClass('opacity-100', (200 < window.scrollY));
        });
    }
    //이외 경로
    else {
        $('#quickBtn').removeClass('d-none');

        //윈도우 scroll 이벤트
        $(window).off('scroll').on('scroll', () => {
            $('#topButton').toggleClass('footer__floating-btn--scroll', (200 < window.scrollY));
        });
    }

    //탑버튼 클릭 이벤트
    $('#topButton').off('click').on('click', () => {
        window.scrollTo({top: 0, behavior: 'smooth'});
    });

    const tempData = [
        {
            label: 'Terms & Condition',
            content: 'Article 1 Consent to Collection of Personal Information and Collection Method\n< The exporum > (hereinafter “Website”) shall establish a procedure for allowing customers to click the button “Agree” to the terms of use, collection of personal information, and details of personal information used. Customers shall be deemed to have agreed to the collection and use of their personal information by clicking the “Agree” button.\n\nArticle 2 Personal Information Items Collected and Purpose of Using Personal Information\n“Personal Information” means information on living persons and refers to their names, email, or any other information that identifies such persons. (Even if such information alone cannot identify a certain person, such information that can be easily combined with other information and be used to identify such a person.)\n\nThe Website has the following purposes for collecting and using customers’ personal information:\n\nOrder information (including members and non-members)\n- Time of collection: Upon placing orders\n\n- Optional collection items: Delivery of messages\n- Purpose of using personal information: Payment and delivery of ordered products\n- Retention period: Retained for five (5) years\n\nArticle 3 Collection of Personal Information via Cookies\nThe Website may install and operate cookies that store and frequently retrieve customers’ information. A cookie means a small amount of text files that a website sends to users’ computer browsers (Internet Explorer, and others)\n1) Purposes of using cookies\n- Providing differentiated information, depending on individuals’ interests\n- Analyzing the access frequency or staying time of users, identifying users’ tastes and interests, and using them for target marketing and as a measure for service improvement\n- Tracing the information on items purchased and items to which users pay attention, and providing tailor-made services\n\n2) Operation of cookies\nCookies are one-time, stored on the Internet Explorer and Chrome side. Cookies identify the user\'s computer but do not identify the user personally.\nYou can also change the settings on your web browser to accept or reject all cookies, or to require confirmation each time a cookie is stored.\n\nArticle 4 Periods of Retaining and Using Personal Information and Destruction of Personal Information\n1) Customers’ personal information shall be destroyed without any delay after the purposes of collection and use of their personal information have been fulfilled. However, if customers’ personal information needs to be retained for a certain period of time for the following purpose of verifying transaction parties’ rights and obligations in accordance with provisions of relevant statutes, such as the Protection of Consumers in e-commerce and Other Transactions Act, such information shall be retained for the specified period:\nA Article 6 of the Protection of Consumers in e-commerce and Other Transactions Act\n- Records on contracts or withdrawal of offers: Retained for five (5) years\n- Records on payments and the supply of goods: Retained for five (5) years\n- Records on the resolution of customers’ complaints or disputes: Retained for three (3) years\n\nC Other related statutes\n2) The Website shall destroy personal information in the following manner.\nA. Destruction procedure-The information entered for membership registration shall be transferred to a separate database (in case of information on paper, a separate filing cabinet), stored for a certain period of time in accordance with internal guidelines and other relevant statutes, and then destroyed.\n- The above personal information shall not be used for any purposes other than for the purposes stipulated by law.\nB. Destruction methods-Personal information printed out on paper shall be destroyed by shredding or burning. - Personal information in electronic file format shall be entirely destroyed by technological methods so that they may not be restored or regenerated.\n3) The Website shall give dormant members (who have not used services for the last twelve (12) months) a notice on the forfeiture of membership in accordance with Article 29.2 of the Act on Promotion of Information and Communications Network Utilization and Information Protection. If such members fail to reply to such a notice, they may be considered to have forfeited their membership at the Website’s discretion. In such a case, dormant members’ personal information may be stored and managed separately from other members’ personal information. Such personal information that is separated and stored shall be destroyed after the lapse of the statutory retention period. In case a customer makes a request, then such a customer’s personal information that is not destroyed shall be made available again at the time of resuming the use of services.\n\nArticle 5 Provision of Personal Information for Third Parties\n1) The Website shall neither use customers’ personal information nor provide such information for other persons, companies, and institutions beyond the scope of Article 1 above (Personal Information Items Collected and Purpose of Using Personal Information).\n2) The following cases are exceptions.\nA. When customers’ personal information is required by relevant institutions for investigative purposes in accordance with relevant statutes\nB. When customers’ personal information is provided in a form that cannot identify certain individuals for advertisers, suppliers, or research organizations to compile statistics or conduct academic or market research\nC. When customers’ personal information is requested in accordance with pre-determined procedures under other relevant statutesEven if personal information is provided in accordance with the foregoing, we do our best to ensure that such information is not indiscriminately provided against the original purpose of collection and use of such information.\n\nArticle 6 Entrustment of Processing Personal Information\n\n1. The Website entrusts personal information to improve its service as follows.\n\nEntrustee : Entrusted Task\nSysforu : Visitor Registration System\n\n2. The Website stated clear the responsibilities on the contract including the purpose of entrustment, restriction on personal information processing, technical/managerial protection, restriction on double entrustment, entrustment supervising, compensation for damage, based on the 「Personal Information Protection Law」 - Article 26, while strictly supervising if the entrustee is handling personal information safely.\n\n\nArticle 7 Withdrawal of Consent to Collection, Use, and Provision of Personal Information\n1) Customers may withdraw their consent to the collection, use, and provision of their personal information that is made available when signing up for membership at any time. They may do so by clicking Withdrawal of Consent (Membership) in the Personal Information Management Menu on the initial landing page of the Website. They may also do so by contacting the chief privacy officer of the Website in writing, by telephone, or through e-mail. Then, the Website shall immediately take necessary measures, such as deletion of personal information. The Website shall immediately notify customers of such measures, including withdrawal of consent and destruction of personal information.\n2) The Website shall take necessary measures to ensure that customers withdraw their consent (membership) to the collection of their information through an easier method than what they used to give their consent to the method of collecting personal information.\n\nArticle 8 Measures for Ensuring the Security of Personal Information\nThe Website shall take technological/administrative/physical measures required for ensuring the security of personal information in accordance with Article 29 of the Personal Information Protection Act.\n1. Encryption of personal information\nUsers’ personal information, including passwords, is stored and managed and is only known to the users who own that information. Important data is secured with separate features, such as the encryption and locking of files and transmitted data.\n2. Technological measures against hacking\nThe Website shall install, regularly update, and check security programs to ensure that personal information is not leaked and damaged due to hacking or computer viruses. It shall also install systems in areas to which access from the outside is controlled, as well as technologically/physically inspect and block such areas.\nArticle 9 Protection of Personal Information of Children under Fourteen Years of Age\nThe Website deems the protection of children’s personal information in online environments to be also important. It does not allow children under fourteen years of age who require the consent of their legal counsel to apply for membership. If such children sign up for the Website or provide their personal information due to the theft of their names and information or abuse of systems, then their legal counsels may exercise all rights.\n\nArticle 10 Chief Privacy Officer\nThe Website appoints the following chief privacy officer who is responsible for the handling of personal information and the handling of customers’ complaints regarding personal information and damage reliefs.\n\n▶ Chief Privacy Officer\n- Name: Andy Park\n- Title: Manager\n- Contact point: ☎ +82 2 6000 6681 / E-mail : andy@exporum.com\n\nArticle 11 Modification of the Guideline on Personal Information Processing\nThis guideline on personal information processing shall take effect on its effective date. Any addition of change under statutes and this guideline, and deletion and correction of anything in this guideline shall be announced via notices seven (7) days prior to the effectuation of such addition, deletion, or correction.'
        },
        {
            label: 'Privacy policy', content: 'Article 1 Purpose\n' +
                'The purpose of these Terms of Use is to set forth any and all matters regarding the terms and conditions of use and operation of services on “World of Coffee Asia”.\n' +
                '\n' +
                'Article 2 Definitions The major terms used herein shall have the following meanings.\n' +
                '1) Members mean users who have registered with the Website by agreeing to the terms and conditions of the Website and providing their personal information and who have executed the user agreement with the Website and use the Website.\n' +
                '2) User Agreement means the agreement entered into by and between the Website and a Member with regard to the use of the Website.\n' +
                '3) Operator means the person/entity that has opened and operated a website that provides services.\n' +
                '4) Termination means that the Members terminate the user agreement.\n' +
                '\n' +
                'Article 3 Applicable Rules Other than Standard Terms of Use\n' +
                'The Operator may separately announce and provide guidance on operational policies, if necessary. If these Terms of Use and operational policies overlap, then the operational policies shall prevail.\n' +
                '\n' +
                'Article 4 Execution of User Agreement\n' +
                '1) The user agreement shall be constituted when a person who wishes to use the Website agrees to these Terms of Use.\n' +
                '2) Any person who wishes to use services as a Member shall indicate his/her intention to agree to these Terms of Use by reading them and clicking “I agree” below during the attendance registration process.\n' +
                '\n' +
                'Article 5 Application for the Use of Services\n' +
                '1) Any user who wishes to use the Website as a Participant shall provide any and all information, such as email address, phone number, and any other requested personal information, which is required by the Website during the attendance registration process.\n' +
                '2) A Member who has not registered his/her genuine information by stealing another person’s information or registering false information may not claim any right to the use of the Website and may be subject to punishment under relevant statutes.\n' +
                '\n' +
                'Article 6 Guideline on Personal Information Processing\n' +
                'Anything related to the Members’ Passwords shall be handled in accordance with the Guideline on Personal Information Processing.\n' +
                'The Operator shall make efforts to protect the Members’ personal information, including the Members’ registered information, in accordance with relevant statutes.\n' +
                'The Members’ personal information shall be protected in accordance with relevant statutes and the Guideline on Personal Information Processing that is established by the Website.\n' +
                'However, the Operator shall have no liability for the information leaked due to a reason attributable to a Member.\n' +
                'If a Member registers and distributes illegal postings, including those that disrupt good public order and customs or violate national security, then the Operator may access the Member’ materials and submit such materials to the related institutions, upon request of such institutions.\n' +
                '\n' +
                'Article 7 Operators’ Obligations\n' +
                '1) The Operator shall deal with any opinions offered or complaints raised by the Members as soon as possible if such opinions or complaints are deemed just. However, if the Operator finds it difficut to deal with such opinions or complaints swiftly due to its personal reasons, then it shall do its best by making a post announcement or sending the Members messages or e-mails.\n' +
                '2) If there is any breakdown in equipment or any equipment is lost, then the Operator may request the Website to repair or restore such equipment without any delay for the continuous and stable provision (or operation) of the Website. However, in case of natural disasters or unavoidable reasons on the part of the Website or the Operator, the operation of the Website may be temporarily suspended.\n' +
                '\n' +
                'Article 8 Members’ Obligations\n' +
                '1) The Members shall comply with the matters stipulated in these Terms of Use, any and all regulations established by the Operator, matters including notices and operational policies to which users are notified via the Website, and relevant statutes. The Members shall not commit acts to disrupt other businesses of the Website or to tarnish the reputation of the Website.\n' +
                '2) The Members may neither assign, give away, nor provide as security the right to use services and their contractual status under the user agreement, unless the Website gives its the express consent.\n' +
                '3) Customers shall take considerable care in managing their IDs and Passwords and may not allow any third party to use their IDs without the consent of the Operator or the Website.\n' +
                '4) The Members shall not infringe on the intellectual property rights of the Operator, the Website, and any third party.\n' +
                '\n' +
                'Article 9 Service Use Hours\n' +
                '1) In principle, services shall be available for 24 hours a day, 365 days a year unless there is any special operational or technological delay. However, the Website may temporarily suspend services on the date(s) or hour(s) designated by the Website for system checkups, extensions, and replacements. As the temporary suspension of services due to scheduled work is announced on the homepage of the Website in advance, users are recommended to visit and read the homepage frequently.\n' +
                '2) The Website may temporarily or permanently suspend services without any prior announcement or notice in the following cases.\n' +
                '- If there is any urgent system checkup, extension, replacement, breakdown, or malfunction\n' +
                '- If there is any force majeure event, such as national emergencies, blackouts, or natural disasters\n' +
                '- If common carriers as stipulated in the Telecommunications Business Act suspend telecommunication services\n' +
                '- If there is any delay in the use of normal services due to overload of traffic during the use of services\n' +
                '3) In case of the suspension of services under Paragraph 2 above, the Website shall notify the Members of such suspension via prior notice. However, if the Website finds it impossible to make a prior notice of the service suspension caused by an uncontrollable cause, then a later notice or announcement shall be made in lieu of such a prior notice.\n' +
                '\n' +
                'Article 10 Termination of Use of Services\n' +
                '1) If Members wish to terminate the user agreement, please send an email to info@worldofcoffee.asia stating your request for termination.\n' +
                '2) As Website-related programs are automatically deleted on the Members Management screen, upon application for termination of the user agreement (or the use of services), the Operator may not be able to look at the information of the applicant for the termination of the user agreement anymore.\n' +
                '\n' +
                'Article 11 Limitations on Use of Services\n' +
                'The Members shall not conduct any act that falls under any of the following. If the Members are found to have committed any such act, the Website may place restrictions on the use of services by the Members, take legal measures, terminate the user agreement, or suspend services for a specified period of time.\n' +
                '1) Registering false information upon signing up for membership or changing information so that it is false after signing up for membership\n' +
                '2) Hindering others from using the Website or stealing others’ information\n' +
                '3) Impersonating the management, staff, or related personnel of the Website\n' +
                '4) Infringing on the moral rights or intellectual property rights of the Website or any other third party or disrupting their businesses or operations\n' +
                '5) Collecting, storing, and disclosing another Member’s personal information without its consent\n' +
                '5) Committing any act that is objectively believed to be related to crimes\n' +
                '7) Violating other relevant statutes\n' +
                '\n' +
                'Article 12 Management of Postings\n' +
                '1) The Operator shall be responsible for the management and operation of postings and materials of the Website. The Operator shall always monitor postings and materials. If the Operator finds or receives a report on faulty postings and materials, then it shall delete such postings and materials and give a warning to the Member that registered such postings and materials.\n' +
                'Meanwhile, as the Member is responsible for postings made by him/her, he or she may not put up postings that violate these Terms of Use.\n' +
                '2) If public institutions, such as the Korea Internet Safety Commission, make a demand for the correction of any posting, then the Operator may delete or move such posting without the Member’s prior consent.\n' +
                '3) The criteria for determining any posting to be faulty are as follows.\n' +
                '- Postings that seriously insult another Member or any third party or tarnish its reputation\n' +
                '- Postings that distribute or offer links to content in violation of good public order and customs\n' +
                '- Postings that promote illegal copies or hacking\n' +
                '- Postings that aim to make advertisements for purposes of profit\n' +
                '- Postings that are objectively acknowledged to be associated with crimes\n' +
                '- Postings that infringe on the rights of another Member or any third party, including copyrights\n' +
                '- Other postings that are deemed to violate relevant statutes\n' +
                '4) If the Website and the Operator are requested by any third party to suspend putting up postings due to reasons such as defamation or infringement of rights, including intellectual property rights, then they may temporarily do so. In addition, if the Website is informed of any lawsuit, settlement between any person/entity requesting the suspension of putting up postings and any person/entity registering such postings, or any decision of a relevant institution similar to the foregoing, then it shall comply with such settlement or decision.\n' +
                '\n' +
                'Article 13 Custody of Postings\n' +
                'If the Operator suspends the operation of the Website due to unavoidable circumstances, then it shall make a prior announcement and make efforts to take all measures to ensure that all postings are easily transferred.\n' +
                '\n' +
                'Article 14 Copyrights to Postings\n' +
                '1) Members shall own copyrights to materials posted by them within the Website. In addition, the Website may not use such materials commercially without the consent of the Members making such postings. However, the Website has the right to use such materials for non-profitable purposes and to post such materials within services.\n' +
                '2) Members may not use materials posted in the services for commercial purposes, including processing and selling the information acquired through the services at their discretion.\n' +
                '3) If the Operator determines materials or content posted or registered by the Members to fall under any of the items in Paragraph of Article 12 above, then it may delete, move, or refuse to register such materials or content without any prior notice.\n' +
                '\n' +
                'Article 15 Damages\n' +
                '1) The Members shall primarily bear all civil and criminal liability that arises within the Website.\n' +
                '2) If a Member suffers from any damage caused by force majeure events, such as natural disasters or its willful misconduct or negligence, then the Website may offer no compensation for damages.\n' +
                '\n' +
                'Article 16 Indemnification\n' +
                '1) If Members do not earn any profit from the rendering of services at the Website or if there arises any damage from the optional selection of service materials or use of services, then the Operator shall be relieved from any liability arising from the foregoing.\n' +
                '2) The Operator shall be relieved from liability caused by any disruption in services at the Website or in telecommunication services offered by other carriers. These Terms of Use shall apply mutatis mutandis to the damage caused in connection with the service base at the Website.\n' +
                '3) The Operator shall not be liable for materials stored, posted, or transmitted by the Members.\n' +
                '4) The Operator shall not be liable for any disruption in the use of services due to a reason attributable to the Members.\n' +
                '5) The Operator shall not be liable for any and all activities, including data transmission and other community activities, between the Members, and between the Members and any third parties, regardless of whether such activities occur within the Website or through any outside services.\n' +
                '6) The Operator shall not be liable for the authenticity, trustworthiness, and accuracy of materials posted or transmitted by the Members or all materials provided for the Members via the Website.\n' +
                '7) The Operator shall not be liable for any and all damage which is caused by goods transactions between the Members, as well as between the Members and any third parties via services.\n' +
                '8) The Operator shall not be liable for any disputes that occur between the Members or between the Members and any third party without a reason attributable to the Operator.\n' +
                '9) The Operator shall not be liable for any damage suffered by the Members if such damage is caused by disruptions in systems that can occur without its willful misconduct or gross negligence. In this case, such misconduct can be considered similar to misconduct in the course of managing, checking, repairing, and replacing equipment, such as servers or operating software. The Operator is also not liable for any damage suffered by the Members if it is caused by any third party’s attack, the distribution of computer viruses for which any anti-virus/vaccines have not been developed by domestic or foreign prestigious research institutions or security-related companies, or force majeure events that cannot be otherwise controlled the Operator.\n' +
                '\n' +
                '\n' +
                'Addendum\n' +
                'These Terms of Use shall take effect on 21-02-2024.'
        },
    ]

    //TODO 모달 공통 함수 생성 시, 공통쪽으로 분리처리 - footer 정책, ticket 구매 화면 정책에서 공통으로 사용 중
    const modal = bootstrap.Modal.getOrCreateInstance(document.getElementById('footerModal'));
    $('.js-evt__policyModal').off('click').on('click', e => {
        const name = e.currentTarget.dataset.name;
        const modalData = (name === 'terms') ? tempData[0] : tempData[1];

        $('#footerModalLabel').text(modalData.label);
        $('#footerModalContent').text(modalData.content);
        modal.show();
    });

});