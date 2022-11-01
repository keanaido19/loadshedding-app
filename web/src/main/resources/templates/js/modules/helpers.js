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