#language:en
Feature: GET info users test Access control service

  #let's start with smoke test
  Scenario: Run request get/info/users and get response 200
    When I run get info users request and get response 200
#   Then I get response 200 (checked by REST assurance function)

  Scenario Outline: GET check users page start to end by table
    When I send request start <start> to end <end> and get response 200
    #Then I get response 200 (checked by REST assurance function)
    #And i get non-null response body (checked by REST assurance function)
    Examples:
      | start | end |
      | 0     | 20  |
      | 0     | 10  |
      | 0     | 5   |
      | 1     | 10  |
      | 2     | 9   |
      | 3     | 20  |
      | 5     | 6   |
      | 4     | 4   |

  Scenario: Check info users by one keyId and one room
    When User keyId 8 enters roomId 4
#   Then I get response 200 (checked by REST assurance function)


