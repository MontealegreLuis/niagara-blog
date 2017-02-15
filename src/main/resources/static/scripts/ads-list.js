(function () {
    var request = $.ajax({
        url: '/ads.json'
    });

    request.done(function (ads) {  // the HTTP response -> an array of JSON objects -> ads
        var i, html = '';

        for (i = 0; i < ads.length; i++) {
            html += '<div><h2>' + ads[i].title + '</h2><p>' + ads[i].description +  '</p></div>';
        }

        $('#load-ads').html(html);
    });
})();
