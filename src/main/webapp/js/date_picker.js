(function () {
    var array = ["2019-10-14","2019-10-15","2019-10-16"]

    $('input').datepicker({
        beforeShowDay: function(date){
            var string = jQuery.datepicker.formatDate('yy-mm-dd', date);
            return [ array.indexOf(string) == -1 ]
        }
    });
})();