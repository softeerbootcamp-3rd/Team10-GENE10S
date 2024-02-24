import classNames from 'classnames'

const BtnDelete = ({ onClick }) => {
  return (
    <div className={classNames('btn-delete')} onClick={onClick}>
      <span>삭제</span>
    </div>
  )
}

export default BtnDelete
