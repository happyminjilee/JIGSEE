import * as React from 'react';
import Box from '@mui/material/Box';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import Typography from '@mui/material/Typography';
import {useStepperStore} from '@/store/stepperstore'
import {useWoDetailStore} from "@/store/workorderstore";

const steps = ['발행', '진행 중', '완료'];

export default function HorizontalLinearStepper() {
    const {activeStep, setActiveStep} = useStepperStore();
    const {} = useWoDetailStore()
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
                {activeStep === steps.length ? (
                    <React.Fragment
                    >
                        <Typography sx={{ mt: 2 }}>All steps completed</Typography>
                    </React.Fragment>
                ) : (
                    <React.Fragment>
                        <Typography sx={{ mt: 2, mb: 1 }}>{steps[activeStep]}</Typography>
                    </React.Fragment>
                )}
            </div>

        </Box>
    );
}
