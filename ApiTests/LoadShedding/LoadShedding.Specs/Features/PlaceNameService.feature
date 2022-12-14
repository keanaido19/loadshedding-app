Feature: PlaceNameService
	Simple tests for Load Shedding PlaceName Service

@GetTowns
Scenario: Get Towns
	Given the province name is "Free State"
	When I get the list of towns in the given province
	Then the list of towns should not be empty
	
@GetTownsFromProvinceThatDoesNotExist
Scenario: Get Towns from a province that does not exist
	Given the province name is "Lol"
	When I get the list of towns in the given province
	Then the list of towns should be empty