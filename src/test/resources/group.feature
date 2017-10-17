Feature: Group Controller

  Scenario: APP VERSION Duplicate Check for Create
    Given the following Add Application Version Request data:
      | no | deviceType | deviceVersionNumber | applicationName | applicationVersionNumber | applicationDownloadUrl                                                  | applicationReleaseDetails                | isForceUpdate | userId |
      | 1  | android    | 4.4.4               |            AEUS | 29                        | https://172.19.225.214/aeus-apks/AEUS-RegServer-release-Reg-1.8.34.apk | App upgrade test                         | true          | sysadmin |
      | 2  | android    | 4.4.4 | AEUS                    | 29         | https://172.19.225.214/aeus-apks/AEUS-RegServer-release-Reg-1.8.34.apk | App upgrade test | true  | sysadmin |
    When call the add application version Client
    Then the application version data should be created with status:
      | no | responseStatusCode | errorCode | errorMsg |
      | 1  | 1                  |        | |
      | 2  | 0                  |  applicationversion.duplicate_application_version_number | "Application Version 29 already available." |     