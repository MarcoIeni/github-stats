# GitHubStats
GitHubStats uses web scraping in order to parse some public information about repositories and users.

example output:

``` json
{
  "repos": [
    {
      "repository": "syl20bnr/spacemacs",
      "stars": 186,
      "commits": 7439,
      "branches": 12,
      "forks": 4589,
      "closedIssues": 4691,
      "openIssues": 2314,
      "projects": 5,
      "closedPulls": 5702,
      "openPulls": 148,
      "watchers": 606
    },
    {
      "repository": "MarcoIeni/intelli-space",
      "stars": 105,
      "commits": 18,
      "branches": 1,
      "forks": 13,
      "closedIssues": 2,
      "openIssues": 0,
      "projects": 0,
      "closedPulls": 0,
      "openPulls": 0,
      "watchers": 7
    }
  ],
  "users": [
    {
      "name": "MarcoIeni",
      "followers": 7,
      "repositories": 12,
      "projects": 0,
      "stars": 22,
      "following": 9
    },
    {
      "name": "syl20bnr",
      "followers": 930,
      "repositories": 162,
      "projects": 0,
      "stars": 812,
      "following": 61
    }
  ]
}
```

Data retrieved from github.com are cached locally.
You can specify cache validity time from settings.

GitHubStats is an attempt to experiment with
[Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
in Kotlin by implementing a project that is more complex than usual examples you can find on the internet.

## Settings
In the data folder you can find two json files.
Default files are given in order to provide examples.

### settings.json
This file contains:
- cacheExpiryTime: seconds of cache validity.
- userProperties: set to true if you want to see that user property in the output.
- projectProperties: set to true if you want to see that project property in the output.

## tracked.json
- users: array of user you want to track.
- proejcts: array of projects you want to track.

## Execution
All parameters are specified from the json settings.
The executable does not support options of any kind.
Just run the executable and you'll see the stats formatted in json in the standard output.
