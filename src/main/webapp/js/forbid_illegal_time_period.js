(function () {
    $("#submitId").on("click", function(e){
        e.preventDefault();
        let checkin = new Date($('#checkin').val());
        let checkout = new Date($('#checkout').val());
        if (checkin > checkout){
            alert("checkin date cannot be less that checkout");
        } else {
            $('#formId').submit();
        }
    });
})();