<script>
Date.prototype.yyyymmdd = function() {
    let mm = this.getMonth() + 1; // getMonth() is zero-based
    let dd = this.getDate();
    return [this.getFullYear(),
        (mm>9 ? '' : '0') + mm,
        (dd>9 ? '' : '0') + dd
    ].join('-');
};

let periods = {};
let currentRoomId;
(function () {
    let bookedRooms = ${rooms};
    console.log(bookedRooms);
    bookedRooms.forEach(room => {
        period = function (from, to) {
            let period = [];
            let i = from;
            while (i <= to){
                period.push(i.yyyymmdd());
                i = new Date(i.setDate(i.getDate() + 1));
            }
            return period;
        }(new Date(room.bookedFrom), new Date(room.bookedTo));
        if (room.room.id in periods){
            periods[room.room.id] = periods[room.room.id].concat(period)
        } else {
            periods[room.room.id] = period;
        }
    });

    for (let [key, value] of Object.entries(periods)) {
        console.log(key + ": \n");
        value.forEach(d => console.log(d + "\n"))
    }
})();

$(document).ready(function(){
    $('.datepicker').datepicker({
        beforeShowDay: function(date){
            let string = jQuery.datepicker.formatDate('yy-mm-dd', date);
            return [ periods[1].indexOf(string) === -1 ]
        }
    });
});
</script>