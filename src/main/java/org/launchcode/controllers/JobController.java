package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job job = jobData.findById(id);
        model.addAttribute("job", job);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (!jobForm.getName().isEmpty()) {
            Employer employer = null;
            Location location = null;
            PositionType positionType = null;
            CoreCompetency coreCompetency = null;

            for (Employer e : jobForm.getEmployers()) {
                if (e.getId() == jobForm.getEmployerId()) {
                    employer = e;
                }
            }

            for (Location l: jobForm.getLocations()) {
                if (l.getId() == jobForm.getLocationId()) {
                    location = l;
                }
            }

            for (PositionType p: jobForm.getPositionTypes()) {
                if (p.getId() == jobForm.getPositionTypeId()) {
                    positionType = p;
                }
            }

            for (CoreCompetency c: jobForm.getCoreCompetencies()) {
                if (c.getId() == jobForm.getCoreCompetencyId()) {
                    coreCompetency = c;
                }
            }

            Job job = new Job(jobForm.getName(), employer, location, positionType, coreCompetency);
            jobData.add(job);
            int jobId = job.getId();
            return "redirect:/job?id="+jobId;
        } else {
            return "new-job";
        }

    }
}
