import classNames from 'classnames';
import { useEffect, useState } from 'react';
import ModalHeader from '../components/ModalHeader';
import ModalDate from '../components/ModalDate';
import ModalInfo from '../components/ModalInfo';
import ModalService from '../components/ModalService';

export default function ReservationModal() {
  const [shopName, setShopName] = useState('');
  const [currentStep, setCurrentStep] = useState('date');
  const [departureYear, setDepartureYear] = useState(null);
  const [departureMonth, setDepartureMonth] = useState(null);
  const [departureDay, setDepartureDay] = useState(null);
  const [departureTime, setDepartureTime] = useState(null);
  const [pickupYear, setPickupYear] = useState(null);
  const [pickupMonth, setPickupMonth] = useState(null);
  const [pickupDay, setPickupDay] = useState(null);
  const [pickupTime, setPickupTime] = useState(null);
  const [phone1, setPhone1] = useState('');
  const [phone2, setPhone2] = useState('');
  const [phone3, setPhone3] = useState('');
  const [sellName, setSellName] = useState('');
  const [plateNumber, setPlateNumber] = useState('');
  const [services, setServices] = useState([]);
  const [request, setRequest] = useState('');
  const [coupon, setCoupon] = useState('');

  //임시 지정
  useEffect(() => {
    setShopName('블루핸즈 인천공항점');
  }, []);

  const dateProps = { departureYear, departureMonth, departureDay, departureTime,
    pickupYear, pickupMonth, pickupDay, pickupTime };
  const infoProps = { phone1, phone2, phone3, sellName, plateNumber };
  const serviceProps = { services, request, coupon };

  function saveInfo(phone1, phone2, phone3, sellName, plateNumber) {
    setPhone1(phone1);
    setPhone2(phone2);
    setPhone3(phone3);
    setSellName(sellName);
    setPlateNumber(plateNumber);
  }

  function dateToInfo(departureYear, departureMonth, departureDay, departureTime,
    pickupYear, pickupMonth, pickupDay, pickupTime) {
    setDepartureYear(departureYear);
    setDepartureMonth(departureMonth);
    setDepartureDay(departureDay);
    setDepartureTime(departureTime);

    setPickupYear(pickupYear);
    setPickupMonth(pickupMonth);
    setPickupDay(pickupDay);
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

  function serviceToInfo(services, request, coupon) {
    setServices(services);
    setRequest(request);
    setCoupon(coupon);
    setCurrentStep('info');
  }

  function changeShop() {
    if (shopName === '블루핸즈 인천공항점') {
      setShopName('블루핸즈 김포공항점');
    } else {
      setShopName('블루핸즈 인천공항점');
    }
    setCurrentStep('date');
    setDepartureYear(null);
    setDepartureMonth(null);
    setDepartureDay(null);
    setDepartureTime(null);
    setPickupYear(null);
    setPickupMonth(null);
    setPickupDay(null);
    setPickupTime(null);
    setPhone1('');
    setPhone2('');
    setPhone3('');
    setSellName('');
    setPlateNumber('');
    setServices([]);
    setRequest('');
    setCoupon('');

    document.querySelector('.shop-selector').classList.remove('dropped');
  }

  return (
    <div className={classNames('modal-page')}>
      <ModalHeader shopName={shopName} currentStep={currentStep} changeShop={changeShop} />
      {currentStep === 'date' ? <ModalDate props={dateProps} nextStep={dateToInfo} /> : null}
      {currentStep === 'info' ? <ModalInfo props={infoProps} prevStep={infoToDate} nextStep={infoToService} /> : null}
      {currentStep === 'service' ? <ModalService props={serviceProps} prevStep={serviceToInfo} /> : null}
    </div>
  );
}
