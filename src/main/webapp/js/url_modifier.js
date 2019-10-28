(function () {
    $("#submitId").on("click", function(e) {
        e.preventDefault();
        let city = $('#cityIn').val();
        $('#formId').attr('action', '/hotels/' + city).submit();
    });
})();