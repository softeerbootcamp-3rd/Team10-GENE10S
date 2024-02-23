import classNames from "classnames";
import moment from "moment";
import BtnDark from "../components/BtnDark";
import SideBar from "../components/SideBar";
import { useRef, useState } from "react";
import { Arrow500 } from "../components/svg/Arrow";
import CustomCalendar from "../components/CustomCalendar";
import { getAvailableTime, addAvailableTime, removeAvailableTime, removeAvailableTimeWithReserv } from "../api/ShopApi";
import { checkReservation } from "../api/ReservationApi";
import AvailableTimeModal from "../components/modal/AvailableTimeModal";

export default function ShopManage() {
    const [availableList, setAvailableList] = useState(null);
    const [openedRow, setOpenedRow] = useState([]);
    const [shopName, setShopName] = useState('블루핸즈 인천공항점');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');

    const [showModal, setShowModal] = useState(false);
    const [state, setState] = useState('');
    const hasReserv = useRef(false);
    const date = useRef('');
    const time = useRef('');

    const [visible, setVisible] = useState(false);

    function resetState() {
        setShowModal(false);
        setVisible(false);
    }

    function handleAccordianClick(event) {
        const target = event.currentTarget;
        if (!target.classList.value.includes('content-date')) return;

        const parent = target.closest('.content-row');
        if (openedRow.includes(parent.id)) {
            openedRow.pop(parent.id);
            setOpenedRow([...openedRow]);
        } else {
            openedRow.push(parent.id);
            setOpenedRow([...openedRow]);
        }
    }

    async function handleTimeClick(event) {
        setShowModal(true);
        const target = event.currentTarget;
        const timeText = target.innerText.split(' : ');
        const timeValue = `${timeText[0]}:${timeText[1]}`;
        const dateValue = target.closest('.content-row').querySelector('.date').innerText;
        time.current = timeValue;
        date.current = dateValue;
        const businessDay = `${date.current} ${time.current}:00`;

        if (target.classList.value.includes('active')) {
            setState('delete');

            await checkReservation(shopName, businessDay)
                .then((response) => {
                    hasReserv.current = response.hasReservation;
                });

        } else {
            hasReserv.current = false;
            setState('add');
        }

        setVisible(true);
    }

    async function handleSearchBtn() {
        await getAvailableTime(shopName, startDate, endDate)
            .then(response => {
                if (response === undefined) return;
                setAvailableList(response);
            });
    }

    async function handleConfirm(date, time, message = '') {
        const businessDay = `${date.current} ${time.current}:00`;
        if (state === 'add') {
            await addAvailableTime(shopName, businessDay)
                .then(() => {
                    addTimeToData(date, time);
                })
                .catch((error) => {
                    console.error(error);
                })
        } else if (state === 'delete') {
            if (hasReserv.current) {
                await removeAvailableTimeWithReserv(shopName, businessDay, message)
                    .then(() => {
                        removeTimeFromData(date, time);
                        resetState();
                    })
                    .catch((error) => {
                        console.error(error);
                    })
            } else {
                await removeAvailableTime(shopName, businessDay)
                    .then(() => {
                        removeTimeFromData(date, time);
                    })
                    .catch((error) => {
                        console.error(error);
                    })
            }
        }

        resetState();
    }

    function handleCancel() {
        resetState();
    }

    function removeTimeFromData(targetDate, targetTime) {
        setAvailableList(prevData => {
            const newData = prevData.map(item => {
                if (item.date === targetDate) {
                    return {
                        ...item,
                        availableTime: item.availableTime.filter(time => time !== targetTime)
                    };
                }
                return item;
            });
            return newData;
        });
    };

    function addTimeToData(targetDate, targetTime) {
        setAvailableList(prevData => {
            const newData = prevData.map(item => {
                if (item.date === targetDate) {
                    return {
                        ...item,
                        availableTime: [...item.availableTime, targetTime]
                    };
                }
                return item;
            });
            return newData;
        });
    };

    function ContentRow() {
        const content = availableList.map((timeData, index) => (
            // <div id={timeData.date} className={classNames('content-row')} key={index}>
            <div id={timeData.date} className={classNames('content-row', { 'opened': openedRow.includes(timeData.date) })} key={index}>
                <div className='content-date' onClick={handleAccordianClick}>
                    <div className='date'>
                        <span>{timeData.date}</span>
                    </div>
                    <div className='btn'>
                        <Arrow500 />
                    </div>
                </div>
                <div className='content-time'>
                    <div id={`${timeData.date}-06:00`} className={classNames('time', { 'active': timeData.availableTime.includes('06:00'), })} onClick={handleTimeClick}>
                        <span> 06 : 00</span>
                    </div>
                    <div id={`${timeData.date}-07:00`} className={classNames('time', { 'active': timeData.availableTime.includes('07:00') })} onClick={handleTimeClick}>
                        <span> 07 : 00</span>
                    </div>
                    <div id={`${timeData.date}-08:00`} className={classNames('time', { 'active': timeData.availableTime.includes('08:00') })} onClick={handleTimeClick}>
                        <span> 08 : 00</span>
                    </div>
                    <div id={`${timeData.date}-09:00`} className={classNames('time', { 'active': timeData.availableTime.includes('09:00') })} onClick={handleTimeClick}>
                        <span> 09 : 00</span>
                    </div>
                    <div id={`${timeData.date}-10:00`} className={classNames('time', { 'active': timeData.availableTime.includes('10:00') })} onClick={handleTimeClick}>
                        <span> 10 : 00</span>
                    </div>
                    <div id={`${timeData.date}-11:00`} className={classNames('time', { 'active': timeData.availableTime.includes('11:00') })} onClick={handleTimeClick}>
                        <span> 11 : 00</span>
                    </div>
                    <div id={`${timeData.date}-12:00`} className={classNames('time', { 'active': timeData.availableTime.includes('12:00') })} onClick={handleTimeClick}>
                        <span> 12 : 00</span>
                    </div>
                    <div id={`${timeData.date}-13:00`} className={classNames('time', { 'active': timeData.availableTime.includes('13:00') })} onClick={handleTimeClick}>
                        <span> 13 : 00</span>
                    </div>
                    <div id={`${timeData.date}-14:00`} className={classNames('time', { 'active': timeData.availableTime.includes('14:00') })} onClick={handleTimeClick}>
                        <span> 14 : 00</span>
                    </div>
                    <div id={`${timeData.date}-15:00`} className={classNames('time', { 'active': timeData.availableTime.includes('15:00') })} onClick={handleTimeClick}>
                        <span> 15 : 00</span>
                    </div>
                    <div id={`${timeData.date}-16:00`} className={classNames('time', { 'active': timeData.availableTime.includes('16:00') })} onClick={handleTimeClick}>
                        <span> 16 : 00</span>
                    </div>
                    <div id={`${timeData.date}-17:00`} className={classNames('time', { 'active': timeData.availableTime.includes('17:00') })} onClick={handleTimeClick}>
                        <span> 17 : 00</span>
                    </div>
                    <div id={`${timeData.date}-18:00`} className={classNames('time', { 'active': timeData.availableTime.includes('18:00') })} onClick={handleTimeClick}>
                        <span> 18 : 00</span>
                    </div>
                    <div id={`${timeData.date}-19:00`} className={classNames('time', { 'active': timeData.availableTime.includes('19:00') })} onClick={handleTimeClick}>
                        <span> 19 : 00</span>
                    </div>
                    <div id={`${timeData.date}-20:00`} className={classNames('time', { 'active': timeData.availableTime.includes('20:00') })} onClick={handleTimeClick}>
                        <span> 20 : 00</span>
                    </div>
                    <div id={`${timeData.date}-21:00`} className={classNames('time', { 'active': timeData.availableTime.includes('21:00') })} onClick={handleTimeClick}>
                        <span> 21 : 00</span>
                    </div>
                </div>
            </div>
        ));
        return content;
    }

    return (
        <div className={classNames('page', 'shop-manage')}>
            <SideBar currentPage={'shop'} />
            <div className='body'>
                <div className='title'>
                    <span>정비소 관리</span>
                </div>
                <div className='content'>
                    <div className='search-area'>
                        <div className='search-row'>
                            <div className='search-item'>
                                <span>정비소</span>
                                <select name="languages" id="lang" className='select' value={shopName} onChange={e => { setShopName(e.target.value) }}>
                                    <option value="블루핸즈 인천공항점">블루핸즈 인천공항점</option>
                                    <option value="블루핸즈 김포공항점">블루핸즈 김포공항점</option>
                                </select>
                            </div>
                        </div>
                        <div className='search-row'>
                            <div className='search-item'>
                                <span>날짜</span>
                                <CustomCalendar onChange={(date) => { setStartDate(moment(date).format('YYYY-MM-DD')) }} value={startDate} />
                                <span>~</span>
                                <CustomCalendar onChange={(date) => { setEndDate(moment(date).format('YYYY-MM-DD')) }} value={endDate} />
                            </div>
                            <BtnDark text={'검색'} onClick={handleSearchBtn} />
                        </div>
                    </div>
                    <div className='column-content'>
                        {availableList && <ContentRow />}
                    </div>
                </div>
            </div>
            {showModal && <div className='modal'></div>}
            {AvailableTimeModal({ status: state, date: date.current, time: time.current, hasReserv: hasReserv.current, onConfirm: handleConfirm, onCancel: handleCancel, visible: visible })}
        </div>
    )
}
