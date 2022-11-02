export function updatePage(templateId, viewModel){
    const template = Handlebars.compile($(`#${templateId}`).html());
    $('#app').html(template(viewModel));
}

async function apiCall(apiCallPath, options) {
    try{
        return await fetch(apiCallPath, options)
            .then(response => {
                return response.json();
            });
    } catch (e) {
        return null;
    }
}

export function getApiCall(apiCallPath) {
    return apiCall(apiCallPath, {method: 'GET'});
}

export function postApiCall(apiCallPath, request) {
    return apiCall(
        apiCallPath,
        {
            method: 'POST',
            body: JSON.stringify(request)
        });
}

function formatDate(date) {
    const dateStringList = date.toDateString().split(" ");
    return `${dateStringList[0]} ${dateStringList[2]}-${dateStringList[1]}-${dateStringList[3]}`;
}

function getTime(timeHM) {
    const [hours, minutes] = timeHM;
    const date = new Date();
    date.setHours(hours, minutes, 0);
    const timeString = date.toLocaleTimeString().split(":");
    return `${timeString[0]}:${timeString[1]}`;
}

function formatTimeSlot(timeSlot) {
    const startHM = timeSlot.start;
    const endHM = timeSlot.end;
    const startTime = getTime(startHM);
    const endTime = getTime(endHM);
    return `${startTime} - ${endTime}`;
}

function formatTimeSlots(timeSlots) {
    const returnList = [];
    for (const timeSlot of timeSlots) {
        returnList.push(formatTimeSlot(timeSlot));
    }
    return returnList;
}

export function formatSchedule(schedule) {
    let [year, month, day] = schedule.startDate;
    const returnList =[];
    for (const daySchedule of schedule.days) {
        const obj = {};
        obj.date = formatDate(new Date(year, month - 1, day));
        obj.slots = formatTimeSlots(daySchedule.slots);
        returnList.push(obj);
        day++;
    }
    return returnList;
}