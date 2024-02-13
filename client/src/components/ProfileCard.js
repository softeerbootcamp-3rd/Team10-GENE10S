import React from 'react';
import { Link } from 'react-router-dom';
import classNames from 'classnames';

const ProfileCard = ({ profileInfo, carList }) => {
  return (
    <div className={classNames('content')}>
      <div className={classNames('profile-view')}>
        { carList && carList.length > 0 && (
          <img src={carList[0].imageUrl} classNames={classNames('profile-image')} />
        )}
        <div className={classNames('profile-info')}>
          <div className={classNames('name')}>
            <div className={classNames('text')}>
              {profileInfo.name}
            </div>
            <Link to="/profile_edit" className={classNames('setting')} />
          </div>
          <div className={classNames('email')}>{profileInfo.imageUrl}</div>
          <div className={classNames('cars')}>
            {carList.map((car, index) => (
              <div key={index} className={classNames('car-name')}>
                <div className={classNames('text')}>{car.sellName}</div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfileCard;
