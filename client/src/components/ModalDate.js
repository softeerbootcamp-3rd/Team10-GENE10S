import classNames from 'classnames';

export default function ModalDate({ setCurrentStep, props }) {
  // const departureDate = props.departureDate;
  // const departureTime = props.departureTime;
  // const pickupDate = props.pickupDate;
  // const pickupTime = props.pickupTime;
  const departureDate = '2024-02-01';
  const departureTime = '8:00';
  const pickupDate = '2024-02-12';
  const pickupTime = '9:00';
  console.log(departureDate === '');

  function handleNext() {
    if (departureDate === '' || departureTime === '' || pickupDate === '' || pickupTime === '') return;
    setCurrentStep(departureDate, departureTime, pickupDate, pickupTime);
  }

  return (
    <div className={classNames('modal_body')}>
      <div className={classNames('frame_left')}>
        <div className={classNames('calender')} />
        <div className={classNames('available-times')} />
      </div>
      <div className={classNames('frame_right')}>
        <div className={classNames('category_row')}>
          <span>1. 센터 방문</span>
          <span>{departureDate === '' ? '센터 방문 시간을 선택해주세요' : `${departureDate} / ${departureTime}`}</span>
        </div>
        <div className={classNames('category_row')}>
          <span>2. 픽업 시간</span>
          <span>{pickupDate === '' ? '픽업 시간을 선택해주세요' : `${pickupDate} / ${pickupTime}`}</span>
        </div>
        <div className={classNames('button')} onClick={handleNext}>
          <span>다음</span>
        </div>
      </div>
    </div>
  );
}
