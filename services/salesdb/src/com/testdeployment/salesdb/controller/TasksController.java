/*Copyright (c) 2016-2017 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.testdeployment.salesdb.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.testdeployment.salesdb.Tasks;
import com.testdeployment.salesdb.service.TasksService;


/**
 * Controller object for domain model class Tasks.
 * @see Tasks
 */
@RestController("salesdb.TasksController")
@Api(value = "TasksController", description = "Exposes APIs to work with Tasks resource.")
@RequestMapping("/salesdb/Tasks")
public class TasksController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class);

    @Autowired
	@Qualifier("salesdb.TasksService")
	private TasksService tasksService;

	@ApiOperation(value = "Creates a new Tasks instance.")
@RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
public Tasks createTasks(@RequestBody Tasks tasks) {
		LOGGER.debug("Create Tasks with information: {}" , tasks);

		tasks = tasksService.create(tasks);
		LOGGER.debug("Created Tasks with information: {}" , tasks);

	    return tasks;
	}

    @ApiOperation(value = "Returns the Tasks instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Tasks getTasks(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Getting Tasks with id: {}" , id);

        Tasks foundTasks = tasksService.getById(id);
        LOGGER.debug("Tasks details with id: {}" , foundTasks);

        return foundTasks;
    }

    @ApiOperation(value = "Updates the Tasks instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Tasks editTasks(@PathVariable("id") Integer id, @RequestBody Tasks tasks) throws EntityNotFoundException {
        LOGGER.debug("Editing Tasks with id: {}" , tasks.getId());

        tasks.setId(id);
        tasks = tasksService.update(tasks);
        LOGGER.debug("Tasks details with id: {}" , tasks);

        return tasks;
    }

    @ApiOperation(value = "Deletes the Tasks instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTasks(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Deleting Tasks with id: {}" , id);

        Tasks deletedTasks = tasksService.delete(id);

        return deletedTasks != null;
    }

    /**
     * @deprecated Use {@link #findTasks(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of Tasks instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Tasks> searchTasksByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Tasks list");
        return tasksService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Tasks instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Tasks> findTasks(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Tasks list");
        return tasksService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Tasks instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Tasks> filterTasks(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Tasks list");
        return tasksService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
    @RequestMapping(value = "/export/{exportType}", method = {RequestMethod.GET,  RequestMethod.POST}, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTasks(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tasksService.export(exportType, query, pageable);
    }

	@ApiOperation(value = "Returns the total count of Tasks instances matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTasks( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting Tasks");
		return tasksService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTasksAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tasksService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TasksService instance
	 */
	protected void setTasksService(TasksService service) {
		this.tasksService = service;
	}

}

