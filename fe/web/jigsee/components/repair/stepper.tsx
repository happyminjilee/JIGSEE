import * as React from 'react';
import Box from '@mui/material/Box';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import {useWoDetailStore} from '@/store/workorderstore'
import Typography from '@mui/material/Typography';
import {useEffect, useState} from 'react'

const steps = ['발행', '진행 중', '완료'];

export default function HorizontalLinearStepper() {

    const {status} = useWoDetailStore();
    const [activeStep, setActiveStep] = useState(1)

    useEffect(() => {
        switch(status) {
            case "PUBLISH":
                setActiveStep(1);
                break;
            case "PROGRESS":
                setActiveStep(2);
                break;
            default:
                setActiveStep(3);
                break;
        }
    }, [status]); // status가 변경될 때만 이 코드 블록이 실행됩니다.
    return (
        <Box sx={{ width: '100%' }}>
            <Stepper activeStep={activeStep} alternativeLabel>
                {steps.map((label, index) => (
                    <Step key={label}>
                        <StepLabel>{label}</StepLabel>
                    </Step>
                ))}
            </Stepper>
            <div
                style={{
                    display: "flex",
                    justifyContent: "center",
                }}
            >
                {activeStep === 3 ? (
                    <React.Fragment
                    >
                        <Typography sx={{ mt: 2 }}>완 료</Typography>
                    </React.Fragment>
                ) : (
                    <React.Fragment>
                        <Typography sx={{ mt: 2, mb: 1 }}>{status}</Typography>
                    </React.Fragment>
                )}
            </div>

        </Box>
    );
}
