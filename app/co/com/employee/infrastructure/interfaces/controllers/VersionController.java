package co.com.employee.infrastructure.interfaces.controllers;

import co.com.employee.BuildInfo;
import play.mvc.Controller;
import play.mvc.Result;


public class VersionController extends Controller {

    public Result version() {
        return ok(BuildInfo.toJson());
    }
}
