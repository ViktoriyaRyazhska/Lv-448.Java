function drawSchedule(json){
    excursionsJson = json;
}


let excursionNames = [];
excursionsJson.forEach(function (element) {
    excursionNames.push({id: element.id, name: element.excursionName});
});

function getUnique(arr, comp) {
    return arr
        .map(e => e[comp])
        .map((e, i, final) => final.indexOf(e) === i && i)
        .filter(e => arr[e]).map(e => arr[e]);
}

const uniqueExcursions = getUnique(excursionNames, "name");

function getUniqueExcursionId(uniqueExcursions, name) {
    let id;
    id = uniqueExcursions.find(ex => ex.name === name).id;
    return id;
}

let excursions = [];

excursionsJson.forEach(function (element) {
    excursions.push({
        name: element.tourGuideFullName,
        location: getUniqueExcursionId(uniqueExcursions, element.excursionName),
        start: new Date(element.dateStart),
        end: new Date(element.dateEnd)
    });
});

console.log(excursions);

function getTimeLineStart(arr) {
    let start = new Date(arr[0].start);
    arr.forEach(function (element) {
        if (element.start < start) {
            start = element.start;
        }
    });
    return start;
}

function getTimeLineEnd(arr) {
    let end = new Date(arr[0].end);
    arr.forEach(function (element) {
        if (element.end > end) {
            end = element.end;
        }
    });
    return end;
}

const timeLineStart = getTimeLineStart(excursions);
const timeLineEnd = getTimeLineEnd(excursions);

/**
 * Visualize schedule
 */
const mySchedule = $('#excursion-schedule').skedTape({
    caption: 'Excursions',
    start: timeLineStart,
    end: timeLineEnd,
    showEventTime: true,
    showEventDuration: true,
    scrollWithYWheel: true,
    locations: uniqueExcursions,
    events: excursions,
    formatters: {
        date: function (date) {
            return $.fn.skedTape.format.date(date, 'l', '.');
        },
        duration: function (start, end, opts) {
            return $.fn.skedTape.format.duration(start, end, {
                hrs: 'г.',
                min: 'хв.'
            });
        },
    }
});