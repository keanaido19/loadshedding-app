Feature: ScheduleService
	Simple tests for Load Shedding Schedule Service

@GetLoadSheddingSchedule
Scenario: Get load shedding schedule
	Given the province name is "Gauteng"
	And the place name is "Ormonde View"
	And the load shedding stage is 0
	When I get the load shedding schedule
	Then the start date should be today
	And the schedule should not be empty
	
@GetLoadSheddingScheduleWithInvalidStage
Scenario: Get load shedding schedule for invalid load shedding stage
	Given the province name is "Gauteng"
	And the place name is "Ormonde View"
	And the load shedding stage is 9
	When I get the load shedding schedule
	Then I should get an error response
	
@GetLoadSheddingScheduleForInvalidProvince
Scenario: Get load shedding schedule for a province that does not exist
	Given the province name is "Pie"
	And the place name is "Ormonde View"
	And the load shedding stage is 0
	When I get the load shedding schedule
	Then the schedule should be empty