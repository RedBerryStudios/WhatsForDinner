package com.redberrystudios.whatsfordinner.endpoints;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @GetMapping("/{g_id}")
    public GroupResponse group(@PathVariable Long g_id) {
        return new GroupResponse(g_id, "Name");
    }

    private static class GroupResponse {
        public Long id;

        public String name;

        public GroupResponse(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

}
