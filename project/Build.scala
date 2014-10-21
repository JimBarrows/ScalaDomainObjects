import sbt._
import Keys._

object ScalaDomainObjectsBuild extends Build {

  lazy val root = Project(id = "ScalaDomainObjects",
    base = file(".")) aggregate (domainCore, peopleAndOrganizations, eCommerce)

  lazy val domainCore = Project(id = "domain-core",
    base = file("domain-core"))

  lazy val peopleAndOrganizations = Project(id = "people-and-organizations",
    base = file("people-and-organizations")) dependsOn (domainCore)

  lazy val products = Project(id = "products",
    base = file("products")) dependsOn ( peopleAndOrganizations)
    
  lazy val eCommerce = Project(id = "e-commerce",
    base = file("e-commerce")) dependsOn ( peopleAndOrganizations)

  lazy val workEffort = Project(id = "work-effort",
    base = file("work-effort")) dependsOn ( peopleAndOrganizations)

  lazy val kanban = Project(id = "kanban", 
      base = file("kanban")) dependsOn (workEffort, peopleAndOrganizations)
}
