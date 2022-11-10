using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using LoadSheddingService.Swagger.Api;
using LoadSheddingService.Swagger.Model;
using NUnit.Framework;
using TechTalk.SpecFlow;

namespace LoadShedding.Specs.Steps;

[Binding]
public sealed class StageServiceStepsDefinition
{
    private StageDO? _stageDo;

    [When(@"I get the current load shedding stage")]
    public void WhenIGetTheCurrentLoadSheddingStage()
    {
        var stageServiceApi = 
            new StageServiceApi("http://localhost:5000/");
        _stageDo = stageServiceApi.GetLoadSheddingStage();
    }

    [Then(@"the current stage should be a valid load shedding stage")]
    public void ThenTheCurrentStageShouldBeAValidLoadSheddingStage()
    {
        var stageNumber = _stageDo!.Stage;
        Assert.That(stageNumber, Is.InRange(0, 8));
    }
}