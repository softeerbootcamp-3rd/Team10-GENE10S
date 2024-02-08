import classNames from 'classnames';
import ModalHeader from '../components/ModalHeader';
import ModalDate from '../components/ModalDate';

export default function ReservationModal() {
  const shopName = '블루핸즈 인천공항점';
  let currentStep = 'date';

  return (
    <>
      <ModalHeader shopName={shopName} currentStep={currentStep} />
      <div className={classNames('reservation-modal')}>{currentStep === 'date' ? <ModalDate /> : null}</div>
    </>
  );
}
