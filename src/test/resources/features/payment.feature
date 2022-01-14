Feature: Make a payment to all client

 Scenario: Make a payment for all client
   Given User logged in EriBank Application
   When User make a payment to "user1" "1122" and "5.00" dollars for all country
   Then User should see their balance drop "5.00" dollars
