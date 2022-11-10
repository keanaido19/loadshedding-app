using LoadSheddingService.Swagger.Api;
using LoadSheddingService.Swagger.Model;
using NUnit.Framework;

namespace LoadShedding.Specs.Steps;

[Binding]
public class PlaceNameServiceStepsDefinition
{
    public static string? Province;
    private List<Town>? _towns;
    
    [Given(@"the province name is ""(.*)""")]
    public void GivenTheProvinceNameIs(string p0)
    {
        Province = p0;
    }

    [When(@"I get the list of towns in the given province")]
    public void WhenIGetTheListOfTownsInTheGivenProvince()
    {
        var placeNameServiceApi = 
            new PlaceNameServiceApi("http://localhost:5000/");
        _towns = placeNameServiceApi.GetTowns(Province!);
    }

    [Then(@"the list of towns should not be empty")]
    public void ThenTheListOfTownsShouldNotBeEmpty()
    {
        Assert.That(_towns, Is.Not.Empty);
    }

    [Then(@"the list of towns should be empty")]
    public void ThenTheListOfTownsShouldBeEmpty()
    {
        Assert.That(_towns, Is.Empty);
    }
}