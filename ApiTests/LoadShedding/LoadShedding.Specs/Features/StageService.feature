Feature: StageService
	Simple tests for Load Shedding Stage Service

@GetStage
Scenario: Get Stage
	When I get the current load shedding stage
	Then the current stage should be a valid load shedding stage