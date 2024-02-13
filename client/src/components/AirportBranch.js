import React from 'react';
import classNames from 'classnames';

const AirportBranch = ({ name, description, imageType }) => {
  return (
    <div className={classNames('type')}>
      <div className={classNames(imageType)}/>
      <div className={classNames('title')}>
        <div className={classNames('name')}>
          {name}
        </div>
        <svg xmlns="http://www.w3.org/2000/svg" width="30" height="20" viewBox="0 0 30 20" fill="none">
          <path d="M1 9.55904H29M29 9.55904L19.102 1.2207M29 9.55904L19.102 18.7812" stroke="#B57B1E" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
        </svg>
      </div>
      <div className={classNames('text-content')}>
        {description}
      </div>
    </div>
  );
};

export default AirportBranch;
