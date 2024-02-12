import classNames from 'classnames';
import Calendar from '../components/calendar';
import ModalHeader from '../components/ModalHeader';

export default function ReservationDate() {

  return (
    <div className={classNames('modal-page')}>
      <ModalHeader />
      <div className={classNames('modal-body')}>
        <div className={classNames('frame-left')}>
          <Calendar year={2024} month={3} />
        </div>
        <div className={classNames('frame-right')}>

        </div>
      </div>
    </div>
  );
}
