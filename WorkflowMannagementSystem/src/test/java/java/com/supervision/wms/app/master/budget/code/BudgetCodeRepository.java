/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.app.master.budget.code;

import com.supervision.wms.app.master.budget.code.model.BudgetCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Thilina Kalum
 */
public interface BudgetCodeRepository extends JpaRepository<BudgetCode, Integer>{
    
}
