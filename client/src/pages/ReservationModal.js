import classNames from 'classnames';
import { useEffect, useState } from 'react';
import ModalHeader from '../components/ModalHeader';
import ModalDate from '../components/ModalDate';
import ModalInfo from '../components/ModalInfo';
import ModalService from '../components/ModalService';
import AlertModal from '../components/AlertModal';

export default function ReservationModal() {
  const [fadeIn, setFadeIn] = useState(false);
  const [alertVisible, setAlertVisible] = useState(false);
  const [modalText, setModalText] = useState(null);

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
  const [customerRequest, setCustomerRequest] = useState('');
  const [coupon, setCoupon] = useState('');

  //임시 지정
  useEffect(() => {
    setShopName('블루핸즈 인천공항점');
    setFadeIn(true);
  }, []);

  const dateProps = { departureYear, departureMonth, departureDay, departureTime,
    pickupYear, pickupMonth, pickupDay, pickupTime };
  const infoProps = { phone1, phone2, phone3, sellName, plateNumber };
  const serviceProps = { services, customerRequest, coupon };

  function showModal(showText) {
    setModalText(showText);
    setAlertVisible(true);
  }

  function closeModal() {
    setAlertVisible(false);
  }

  function saveInfo(phone1, phone2, phone3, sellName, plateNumber) {
    setPhone1(phone1);
    setPhone2(phone2);
    setPhone3(phone3);
    setSellName(sellName);
    setPlateNumber(plateNumber);
  }

  function dateToInfo(departureYear, departureMonth, departureDay, departureTime,
    pickupYear, pickupMonth, pickupDay, pickupTime) {

    if (departureTime === null) {
      showModal('센터 방문 시각을 입력해주세요.');
      return;
    }
    if (pickupTime === null) {
      showModal('픽업 시각을 입력해주세요.');
      return;
    }

    setDepartureYear(departureYear);
    setDepartureMonth(departureMonth);
    setDepartureDay(departureDay);
    setDepartureTime(departureTime);

    setPickupYear(pickupYear);
    setPickupMonth(pickupMonth);
    setPickupDay(pickupDay);
    setPickupTime(pickupTime);
    
    handleStepChange('info');
  }

  function infoToDate(phone1, phone2, phone3, sellName, plateNumber) {
    saveInfo(phone1, phone2, phone3, sellName, plateNumber);
    handleStepChange('date');
  }

  function infoToService(phone1, phone2, phone3, sellName, plateNumber) {

    if (phone1 === null || phone1 === ''
      || phone2 === null || phone2 === ''
      || phone3 === null || phone3 === '') {
      showModal('전화번호를 입력해주세요.');
      return;
    }
    if (sellName === null || sellName === '') {
      showModal('차종을 입력해주세요.');
      return;
    }
    if (plateNumber === null | plateNumber === '') {
      showModal('차량 번호를 입력해주세요.');
      return;
    }
    saveInfo(phone1, phone2, phone3, sellName, plateNumber);
    handleStepChange('service');
  }

  function serviceToInfo(services, customerRequest, coupon) {
    setServices(services);
    setCustomerRequest(customerRequest);
    setCoupon(coupon);
    handleStepChange('info');
  }

  const submitForm = (services, customerRequest, coupon, couponValid) => {
    if (coupon != null && coupon != '' && !couponValid) {
      showModal('쿠폰번호를 확인해주세요.');
      return;
    }
    setServices(services);
    setCustomerRequest(customerRequest);
    setCoupon(coupon);
    
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
    setCustomerRequest('');
    setCoupon('');

    document.querySelector('.shop-selector').classList.remove('dropped');
  }

  const handleStepChange = (nextStep) => {
    setFadeIn(false);
    setTimeout(() => {
      setCurrentStep(nextStep);
      setFadeIn(true);
    }, 300);
  };

  return (
    <>
      <div className={classNames('modal-page')}>
        <ModalHeader shopName={shopName} currentStep={currentStep} changeShop={changeShop} />
        <div className={classNames('modal-content', {'fade-in': fadeIn})}>
          {currentStep === 'date' ? <ModalDate props={dateProps} nextStep={dateToInfo} /> : null}
          {currentStep === 'info' ? <ModalInfo props={infoProps} prevStep={infoToDate} nextStep={infoToService} /> : null}
          {currentStep === 'service' ? <ModalService props={serviceProps} prevStep={serviceToInfo} submit={submitForm} /> : null}
        </div>
      </div>
      <AlertModal onClose={closeModal} showText={modalText} visible={alertVisible}/>
    </>
  );
}
