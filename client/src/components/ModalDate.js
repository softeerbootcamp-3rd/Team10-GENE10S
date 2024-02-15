import classNames from 'classnames';
import { BtnBlack } from './Button';
import { useEffect, useState } from 'react';
import Calendar from './Calendar';
import TimeSlots from './TimeSlots';
import { getAvailableDate, getAvailableTime } from '../api/ReservationApi';

export default function ModalDate({ nextStep, props, fadeIn }) {

  const currentYear = new Date().getFullYear();
  const currentMonth = new Date().getMonth();

  const [departureYear, setDepartureYear] = useState(props.departureYear);
  const [departureMonth, setDepartureMonth] = useState(props.departureMonth);
  const [departureDay, setDepartureDay] = useState(props.departureDay);
  const [departureTime, setDepartureTime] = useState(props.departureTime);

  const [pickupYear, setPickupYear] = useState(props.pickupYear);
  const [pickupMonth, setPickupMonth] = useState(props.pickupMonth);
  const [pickupDay, setPickupDay] = useState(props.pickupDay);
  const [pickupTime, setPickupTime] = useState(props.pickupTime);

  const [availableDates, setAvailableDates] = useState([]);
  const [showTimes, setShowTimes] = useState(false);
  const [timeData, setTimeData] = useState(null);
  const [selectedTime, setSelectedTime] = useState(null);

  const [selectedInput, setSelectedInput] = useState('departure');
  
  const [calendarYear, setCalendarYear] = useState(currentYear);
  const [calendarMonth, setCalendarMonth] = useState(currentMonth);
  const [calendarDay, setCalendarDay] = useState(null);

  useEffect(() => {
    getAvailableDate().then((result) => {
      setAvailableDates(result.availableDates);
    });
  });

  const handleNext = () => {
    if (departureTime === '' || pickupTime === '') return;
    nextStep(departureYear, departureMonth, departureDay, departureTime,
      pickupYear, pickupMonth, pickupDay, pickupTime);
  }

  const handleLastMonth = () => {
    setShowTimes(false);
    setCalendarDay(null);
    if (currentMonth === 0) {

      if (selectedInput === 'departure'
        && calendarYear - 1 === departureYear
        && 11 === departureMonth) {
        setCalendarDay(departureDay);
        setShowTimes(true);
      } else if (selectedInput === 'pickup'
        && calendarYear - 1 === pickupYear
        && 11 === pickupMonth) {
        setCalendarDay(pickupDay);
        setShowTimes(true);
      }

      setCalendarMonth(11);
      setCalendarYear(calendarYear - 1);
    } else {
      
      if (selectedInput === 'departure'
        && calendarYear === departureYear
        && calendarMonth - 1 === departureMonth) {
        setCalendarDay(departureDay);
        setShowTimes(true);
      } else if (selectedInput === 'pickup'
        && calendarYear === pickupYear
        && calendarMonth - 1 === pickupMonth) {
        setCalendarDay(pickupDay);
        setShowTimes(true);
      }

      setCalendarMonth(calendarMonth - 1);
    }
  };

  const handleNextMonth = () => {
    setShowTimes(false);
    setCalendarDay(null);

    if (currentMonth === 11) {

      if (selectedInput === 'departure'
        && calendarYear + 1 === departureYear
        && 0 === departureMonth) {
        setCalendarDay(departureDay);
        setShowTimes(true);
      } else if (selectedInput === 'pickup'
        && calendarYear + 1 === pickupYear
        && 0 === pickupMonth) {
        setCalendarDay(pickupDay);
        setShowTimes(true);
      }

      setCalendarMonth(0);
      setCalendarYear(calendarYear + 1);
    } else {

      if (selectedInput === 'departure'
        && calendarYear === departureYear
        && calendarMonth + 1 === departureMonth) {
        setCalendarDay(departureDay);
        setShowTimes(true);
      } else if (selectedInput === 'pickup'
        && calendarYear === pickupYear
        && calendarMonth + 1 === pickupMonth) {
        setCalendarDay(pickupDay);
        setShowTimes(true);
      }

      setCalendarMonth(calendarMonth + 1);
    }
  };

  const handleClickDate = (year, month, day) => {
    setCalendarDay(day);
    if (selectedInput === 'departure') {
      setDepartureYear(year);
      setDepartureMonth(month);
      setDepartureDay(day);
      setSelectedTime(null);
      setDepartureTime(null);
    } else if (selectedInput === 'pickup') {
      setPickupYear(year);
      setPickupMonth(month);
      setPickupDay(day);
      setSelectedTime(null);
      setPickupTime(null);
    }

    getAvailableTime(year, month, day).then(result => {
      setTimeData(result.timeSlots);
      setShowTimes(true);
    });
  }

  const handleClickTime = (time) => {
    if (selectedTime === time) {
      setSelectedTime(null);
      if (selectedInput === 'departure')
        setDepartureTime(null);
      else
        setPickupTime(null);
    }
    else {
      setSelectedTime(time);
      if (selectedInput === 'departure')
        setDepartureTime(time);
      else
        setPickupTime(time);
    }
  }

  const handleClickDeparture = () => {
    if (selectedInput === 'departure')
      return;
    setSelectedInput('departure');
    setCalendarYear(departureYear != null ? departureYear : currentYear);
    setCalendarMonth(departureMonth != null ? departureMonth : currentMonth);
    setCalendarDay(departureDay);
    if (departureDay != null) {
      getAvailableTime(departureYear, departureMonth, departureDay).then(result => {
        setTimeData(result.timeSlots);
        setShowTimes(true);
      });
    }
    setSelectedTime(departureTime);
  }

  const handleClickPickup = () => {
    if (selectedInput === 'pickup')
      return;
    setSelectedInput('pickup');
    setCalendarYear(pickupYear != null ? pickupYear : currentYear);
    setCalendarMonth(pickupMonth != null ? pickupMonth : currentMonth);
    setCalendarDay(pickupDay);
    if (pickupDay != null) {
      getAvailableTime(pickupYear, pickupMonth, pickupDay).then(result => {
        setTimeData(result.timeSlots);
        setShowTimes(true);
      });
    }
    setSelectedTime(pickupTime);
  }

  return (
    <>
      <div className={classNames('body', {'fade-in': fadeIn})}>
        <div className={classNames('frame-left')}>
          <Calendar
            year={calendarYear}
            month={calendarMonth}
            day={calendarDay}
            availableDates={availableDates}
            handleClickDate={handleClickDate}
            handleLastMonth={handleLastMonth}
            handleNextMonth={handleNextMonth}
          />
          {showTimes && <TimeSlots timeSlots={timeData} selectedTime={selectedTime} handleClickTime={handleClickTime} />}
        </div>
        <div className={classNames('frame-right')}>
          <div className={classNames('category-row')}>
            <span className={classNames('title')}>1. 센터 방문</span>
            <span className={classNames('content', 'hover-pointer', selectedInput === 'departure' ? 'active' : null)} onClick={() => handleClickDeparture()}>
              <div className={classNames('input-area', 'w-200')}>
                <span className={classNames('input-text')}>{departureDay != null ? departureYear + '년 ' + (departureMonth + 1) + '월 ' + departureDay + '일' : null}</span>
              </div>
              <div className={classNames('input-area', 'w-160')}>
                <span className={classNames('input-text')}>{departureTime != null ? departureTime + ' : 00' : null}</span>
              </div>
            </span>
          </div>
          <div className={classNames('category-row')}>
            <span className={classNames('title')}>2. 픽업 시각</span>
            <span className={classNames('content', 'hover-pointer', selectedInput === 'pickup' ? 'active' : null)} onClick={() => handleClickPickup()}>
              <div className={classNames('input-area', 'w-200')}>
                <span className={classNames('input-text')}>{pickupDay != null ? pickupYear + '년 ' + (pickupMonth + 1) + '월 ' + pickupDay + '일' : null}</span>
              </div>
              <div className={classNames('input-area', 'w-160')}>
                <span className={classNames('input-text')}>{pickupTime != null ? pickupTime + ' : 00' : null}</span>
              </div>
            </span>
          </div>
        </div>
      </div>
      <div className={classNames('btn-group')}>
        <BtnBlack text="다음" onClick={handleNext} />
      </div>
    </>
  );
}
