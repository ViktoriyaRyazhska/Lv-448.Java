// helpers
function today(hours, minutes) {
    var date = new Date();
    date.setUTCHours(hours, minutes, 0, 0);
    return date;
}
function yesterday(hours, minutes) {
    var date = today(hours, minutes);
    date.setTime(date.getTime() - 24 * 60 * 60 * 1000);
    return date;
}
function tomorrow(hours, minutes) {
    var date = today(hours, minutes);
    date.setTime(date.getTime() + 24 * 60 * 60 * 1000);
    return date;
}

function startDate() {
    var date = new Date();
    return date;
}

function endDate() {

}

// events
var events = [
    {
        name: 'Meeting 1',
        location: 'london',
        start: today(4, 15),
        end: today(7, 30),
    },
    {
        name: 'Meeting 2',
        location: 'london',
        start: today(7, 30),
        end: today(9, 15)
    },
    {
        name: 'Meeting',
        location: '1',
        start: today(10, 0),
        end: today(11, 30)
    },
    // more events here
];

//{id: '6', name: 'Berlin', tzOffset: -2 * 60},

var locations = [
    {id: '1', name: 'San Francisco', tzOffset: 7 * 60},
    {id: '2', name: 'Sydney', tzOffset: -10 * 60},
    {id: '3', name: 'New York', tzOffset: 4 * 60},
    {id: 'london', name: 'London', tzOffset: -1 * 60},
    {id: '5', name: 'Copenhagen', tzOffset: -2 * 60},
    {id: '6', name: 'Berlin', tzOffset: -2 * 60},
];

var mySchedule = $('#schedule').skedTape({
    caption: 'Excursions',
    start: yesterday(22, 0),
    end: today(12, 0),
    showEventTime: true,
    showEventDuration: true,
    scrollWithYWheel: true,
    locations: locations,
    events: events,
    formatters: {
        date: function (date) {
            return $.fn.skedTape.format.date(date, 'l', '.');
        },
        duration: function (start, end, opts) {
            return $.fn.skedTape.format.duration(start, end, {
                hrs: 'ч.',
                min: 'мин.'
            });
        },
    }
});