window.addEventListener('load', () => {
    const fileInfo = {
        url: 'https://kr.object.ncloudstorage.com/bucket-exporum-prod/woc/resource/bangkok/partners/sponsorship-prgrambook.pdf',
        downloadName: 'Sponsorship program book.pdf'
    };

    setDownloadFile('web', 'sponsorshipDownload', fileInfo);
});