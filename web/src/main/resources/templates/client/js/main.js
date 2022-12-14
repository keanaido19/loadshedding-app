import * as helpers from "./helpers.js";

async function getLoadSheddingStage() {
    const data =
        await helpers.getApiCall("http://localhost:5000/stage");

    if (null === data) return;

    const viewModel = {
        stage: `Load Shedding (Stage ${data.stage})`,
    };
    helpers.updatePage("home-template", viewModel);
}

async function getTowns() {
    const province = location.hash.substring(location.hash.lastIndexOf('=') + 1);

    const data = await helpers.getApiCall(
        `http://localhost:5000/towns/${province}`);

    const viewModel = {
        province: province.replace("%20", " "),
        towns: data
    };

    helpers.updatePage("towns-template", viewModel);
}

async function getSchedule() {
    const hashLocationList = location.hash.split("?");

    let province =
        hashLocationList[1].substring(hashLocationList[1].lastIndexOf('=') + 1);
    let town =
        hashLocationList[2].substring(hashLocationList[2].lastIndexOf('=') + 1);
    const stage =
        (await helpers.getApiCall("http://localhost:5000/stage")).stage;

    const data =
        await helpers.getApiCall(
            `http://localhost:5000/${province}/${town}/${stage}`
        );

    if (null === data) return;

    province = province.replace("%20", " ");
    town = town.replace("%20", " ");

    const viewModel = {
        details: `${town}, ${province} (Stage ${stage})`,
        schedules: helpers.formatSchedule(data),
    };
    helpers.updatePage("schedule-template", viewModel);
}

window.addEventListener('load', () => {
    const app = $('#app');

    const loadingTemplate = Handlebars.compile($('#loading-template').html());

    const router = new Router({
        mode:'hash',
        root:'/',
        page404: (path) => {
            router.navigateTo('/home');
        }
    });

    router.add('/home', async () => {
        app.html(loadingTemplate);
        await getLoadSheddingStage();
    });

    router.add('/towns', async () => {
        app.html(loadingTemplate);
        await getTowns();
    });

    router.add('/schedule', async () => {
        app.html(loadingTemplate);
        await getSchedule();
    });

    router.addUriListener();

    $('a').on('click', (event) => {
        event.preventDefault();
        const target = $(event.target);
        const href = target.attr('href');
        const path = href.substring(href.lastIndexOf('/'));
        router.navigateTo(path);
    });

    router.navigateTo('/');
});
