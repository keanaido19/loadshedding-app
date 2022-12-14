using LoadSheddingService.Swagger.Api;
using LoadSheddingService.Swagger.Client;
using LoadSheddingService.Swagger.Model;
using Newtonsoft.Json;
using NUnit.Framework;

namespace LoadShedding.Specs.Steps;

[Binding]
public class ScheduleServiceStepsDefinition
{
    private static readonly DateTime DateTime = DateTime.Now;
    
    private readonly List<int> _startDate = 
        new() {DateTime.Year, DateTime.Month, DateTime.Day};
    
    private string? _place;
    private int _stage;
    private ScheduleDO? _scheduleDO;
    private ApiException? _apiException;
    
    [Given(@"the place name is ""(.*)""")]
    public void GivenThePlaceNameIs(string p0)
    {
        _place = p0;
    }

    [Given(@"the load shedding stage is (.*)")]
    public void GivenTheLoadSheddingStageIs(int p0)
    {
        _stage = p0;
    }

    [When(@"I get the load shedding schedule")]
    public void WhenIGetTheLoadSheddingSchedule()
    {
        var scheduleServiceApi = 
            new ScheduleServiceApi("http://localhost:5000/");

        try
        {
            _scheduleDO = scheduleServiceApi.GetSchedule(
                PlaceNameServiceStepsDefinition.Province!, _place!, _stage
            );
        }
        catch (ApiException e)
        {
            _apiException = e;
        }
        
    }

    [Then(@"the start date should be today")]
    public void ThenTheStartDateShouldBeToday()
    {
        Assert.That(_scheduleDO!.StartDate, Is.EquivalentTo(_startDate));
    }
    
    [Then(@"the schedule should not be empty")]
    public void ThenTheScheduleShouldNotBeEmpty()
    {
        Assert.That(_scheduleDO!.Days, Is.Not.Empty);
    }

    [Then(@"I should get an error response")]
    public void ThenIShouldGetAnErrorResponse()
    {
        Assert.That(_apiException!.ErrorCode, Is.EqualTo(400));
        Assert.That(_apiException!.ErrorContent, 
            Is.EqualTo("{\"loadsheddingstage\":[{\"message\":\"Invalid " +
                       "Load Shedding stage!\",\"args\":{},\"value\":9}]}"
                       ));
    }

    [Then(@"the schedule should be empty")]
    public void ThenTheScheduleShouldBeEmpty()
    {
        Assert.That(_apiException!.ErrorCode, Is.EqualTo(404));
        
        var scheduleDO = JsonConvert.DeserializeObject<ScheduleDO>(
            _apiException!.ErrorContent.ToString()!
            );
        
        Assert.That(scheduleDO!.Days, Is.Empty);
        Assert.That(scheduleDO!.StartDate, Is.EquivalentTo(_startDate));
    }
}