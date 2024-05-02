import {create} from 'zustand';

interface Stepper {
    activeStep: number;
    setActiveStep: (step: number) => void;
}

export const useStepperStore = create<Stepper>((set) => ({
    activeStep: 1,
    setActiveStep: (step:number) => {set({activeStep:step})}
}))