import classNames from 'classnames';
import ModalHeader from '../components/ModalHeader';
import ModalDate from '../components/ModalDate';
import { useState } from 'react';

export default function ReservationModal() {
  const shopName = '블루핸즈 인천공항점';
  const [currentStep, setCurrentStep] = useState('date');
  const [departureDate, setDepartureDate] = useState('');
  const [departureTime, setDepartureTime] = useState('');
  const [pickupDate, setPickupDate] = useState('');
  const [pickupTime, setPickupTime] = useState('');

  function handleDate(departureDate, departureTime, pickupDate, pickupTime) {
    console.log(departureDate, departureTime, pickupDate, pickupTime);
    setDepartureDate(departureDate);
    setDepartureTime(departureTime);
    setPickupDate(pickupDate);
    setPickupTime(pickupTime);
    setCurrentStep('info');
  }

  const dateProps = { departureDate, departureTime, pickupDate, pickupTime };

  return (
    <>
      <ModalHeader shopName={shopName} currentStep={currentStep} />
      <div className={classNames('reservation-modal')}>
        {currentStep === 'date' ? <ModalDate setCurrentStep={handleDate} props={dateProps} /> : null}
      </div>
    </>
  );
}
