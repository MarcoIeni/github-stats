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
in Kotlin by implementing a project that is more complex than usual online examples.

## Settings
In the data folder you can find two json files.
Default files are given: edit them.

### settings.json
This file contains:
- cacheExpiryTime: seconds of cache validity. For example, if it is 10 and you
  retrieve the stats again after a time < 10 seconds, then the output will be
  the same, because stats are read from your local copy, which was saved in
  data/cache.json.
- userProperties: set to true if you want to see that user property in the output.
- projectProperties: set to true if you want to see that project property in the output.

## tracked.json
This file contains:
- users: array of user you want to track.
- proejcts: array of projects you want to track.

## Execution
All parameters are specified from the json settings.
The executable does not support options of any kind.
Just run the executable and you'll see the stats formatted in json in the standard output.

## FAQ
### Why contributors count and releases are not present?
This project works with data scraping, i.e. it loads the html and retrieve data from there.

When you have a slow connection contributors are not loaded immediately: "fetching contributors" is showed at first instead.

I don't know how to tell Jsoup to wait until "fetching contributors" disappears in order to retrieve contributors.

Releases are not retrieved because sometimes contributors are loaded
and sometimes they are not and so the scraper cannot understand if a projects
tab is present or not by simply counting the elements of the navigation bar.

Releases could be added by checking if the word "packages" is present in the navigation bar for example.
If you want to play with Kotlin and Clean architecture, a pull request is welcome!
