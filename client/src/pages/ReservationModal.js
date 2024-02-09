import classNames from 'classnames';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ModalHeader from '../components/ModalHeader';
import ModalDate from '../components/ModalDate';
import ModalInfo from '../components/ModalInfo';

export default function ReservationModal() {
  const navigate = useNavigate();

  const shopName = '블루핸즈 인천공항점';
  const [currentStep, setCurrentStep] = useState('date');
  const [departureDate, setDepartureDate] = useState('');
  const [departureTime, setDepartureTime] = useState('');
  const [pickupDate, setPickupDate] = useState('');
  const [pickupTime, setPickupTime] = useState('');
  const [phone1, setPhone1] = useState('');
  const [phone2, setPhone2] = useState('');
  const [phone3, setPhone3] = useState('');
  const [sellName, setSellName] = useState('');
  const [plateNumber, setPlateNumber] = useState('');

  const dateProps = { departureDate, departureTime, pickupDate, pickupTime };
  const infoProps = { phone1, phone2, phone3, sellName, plateNumber };

  function saveInfo(phone1, phone2, phone3, sellName, plateNumber) {
    setPhone1(phone1);
    setPhone2(phone2);
    setPhone3(phone3);
    setSellName(sellName);
    setPlateNumber(plateNumber);
  }

  function dateToInfo(departureDate, departureTime, pickupDate, pickupTime) {
    setDepartureDate(departureDate);
    setDepartureTime(departureTime);
    setPickupDate(pickupDate);
    setPickupTime(pickupTime);
    setCurrentStep('info');
  }

  function infoToDate(phone1, phone2, phone3, sellName, plateNumber) {
    saveInfo(phone1, phone2, phone3, sellName, plateNumber);
    setCurrentStep('date');
  }

  function infoToService(phone1, phone2, phone3, sellName, plateNumber) {
    saveInfo(phone1, phone2, phone3, sellName, plateNumber);
    setCurrentStep('service');
  }

  function toPrev() {
    navigate('/');
  }

  return (
    <>
      <ModalHeader shopName={shopName} currentStep={currentStep} />
      <div className={classNames('modal-page')}>
        {currentStep === 'date' ? <ModalDate prevStep={toPrev} nextStep={dateToInfo} props={dateProps} /> : null}
        {currentStep === 'info' ? <ModalInfo prevStep={infoToDate} nextStep={infoToService} props={infoProps} /> : null}
      </div>
    </>
  );
}
