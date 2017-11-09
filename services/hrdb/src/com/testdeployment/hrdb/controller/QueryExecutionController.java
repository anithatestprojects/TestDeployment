/*Copyright (c) 2016-2017 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/

package com.testdeployment.hrdb.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.runtime.data.dao.query.WMQueryExecutor;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import com.testdeployment.hrdb.Employee;
import com.testdeployment.hrdb.service.HrdbQueryExecutorService;
import com.testdeployment.hrdb.models.query.*;

@RestController(value = "Hrdb.QueryExecutionController")
@RequestMapping("/hrdb/queryExecutor")
@Api(value = "QueryExecutionController", description = "controller class for query execution")
public class QueryExecutionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryExecutionController.class);

    @Autowired
    private HrdbQueryExecutorService queryService;

    @RequestMapping(value = "/queries/Employee_HSQL", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Employee_HSQL")
    public Page<Employee> executeEmployee_HSQL(Pageable pageable, HttpServletRequest _request) {
        LOGGER.debug("Executing named query: Employee_HSQL");
        Page<Employee> _result = queryService.executeEmployee_HSQL(pageable);
        LOGGER.debug("got the result for named query: Employee_HSQL, result:{}", _result);
        return _result;
    }

    @ApiOperation(value = "Returns downloadable file for query Employee_HSQL")
    @RequestMapping(value = "/queries/Employee_HSQL/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportEmployee_HSQL(@PathVariable("exportType") ExportType exportType, Pageable pageable, HttpServletRequest _request) {
        LOGGER.debug("Exporting named query: Employee_HSQL");

        return queryService.exportEmployee_HSQL(exportType, pageable);
    }

    @RequestMapping(value = "/queries/Employee_NativeSQL", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Employee")
    public Page<EmployeeNativeSqlResponse> executeEmployee_NativeSQL(Pageable pageable, HttpServletRequest _request) {
        LOGGER.debug("Executing named query: Employee_NativeSQL");
        Page<EmployeeNativeSqlResponse> _result = queryService.executeEmployee_NativeSQL(pageable);
        LOGGER.debug("got the result for named query: Employee_NativeSQL, result:{}", _result);
        return _result;
    }

    @ApiOperation(value = "Returns downloadable file for query Employee_NativeSQL")
    @RequestMapping(value = "/queries/Employee_NativeSQL/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportEmployee_NativeSQL(@PathVariable("exportType") ExportType exportType, Pageable pageable, HttpServletRequest _request) {
        LOGGER.debug("Exporting named query: Employee_NativeSQL");

        return queryService.exportEmployee_NativeSQL(exportType, pageable);
    }

}


