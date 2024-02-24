import classNames from 'classnames'

const InfoTable = ({ children }) => {
  return (
    <div className={classNames('info-table')}>
      <div className={classNames('tbody')}>{children}</div>
    </div>
  )
}

export default InfoTable
