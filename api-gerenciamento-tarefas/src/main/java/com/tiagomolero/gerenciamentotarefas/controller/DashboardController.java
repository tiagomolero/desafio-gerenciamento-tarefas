package com.tiagomolero.gerenciamentotarefas.controller;

import com.tiagomolero.gerenciamentotarefas.model.dashboard.DashboardResponseDTO;
import com.tiagomolero.gerenciamentotarefas.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponseDTO> gerarDashboard(){
        DashboardResponseDTO dashboardResponseDTO = dashboardService.gerarDashboard();
        return ResponseEntity.ok(dashboardResponseDTO);
    }

}
