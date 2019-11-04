resolvers += Resolver.url(
  "Twitter repository",
  url("https://bintray.com/twittercsl/sbt-plugins/"))(
  Resolver.ivyStylePatterns)

addSbtPlugin("com.twitter" %% "scrooge-sbt-plugin" % "19.10.0")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.5")