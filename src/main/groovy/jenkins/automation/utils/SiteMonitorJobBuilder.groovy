package jenkins.automation.utils

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

class SiteMonitorJobBuilder {

    String name
    String description
    List<String> emails
    String cronSchedule = 'H/15 * * * *'

    List<String> urls

    Job build(DslFactory factory) {

        def baseJob = new BaseJobBuilder(
                name: this.name,
                description: this.description,
                emails: this.emails,
        ).build(factory)


        baseJob.with {
            triggers {
                cron(cronSchedule)
            }
            configure { project ->
                project / publishers << 'hudson.plugins.sitemonitor.SiteMonitorRecorder' {
                    mSites {
                        'hudson.plugins.sitemonitor.model.Site' {
                            mUrl 'http://google.com'
                        }
                        'hudson.plugins.sitemonitor.model.Site' {
                            mUrl 'http://foo.com'
                        }
                    }
                }
            }

        }

        return baseJob
    }
}

