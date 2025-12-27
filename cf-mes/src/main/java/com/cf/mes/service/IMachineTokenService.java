package com.cf.mes.service;

import com.cf.mes.domain.MachineToken;

public interface IMachineTokenService {


    public void InitializeMachineRegistrationCodeList();


    public boolean InsertMachineRegistrationCodeList(Long machineId,String registrationCode);


    public boolean RemoveMachineRegistrationCodeList(Long machineId,String registrationCode);


    public MachineToken GetToken(Long machineId,String registrationCode);


    public void SetToken(MachineToken machineToken);

    MachineToken GetToken();
}
