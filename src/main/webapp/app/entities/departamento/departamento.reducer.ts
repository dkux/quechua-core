import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDepartamento, defaultValue } from 'app/shared/model/departamento.model';

export const ACTION_TYPES = {
  FETCH_DEPARTAMENTO_LIST: 'departamento/FETCH_DEPARTAMENTO_LIST',
  FETCH_DEPARTAMENTO: 'departamento/FETCH_DEPARTAMENTO',
  CREATE_DEPARTAMENTO: 'departamento/CREATE_DEPARTAMENTO',
  UPDATE_DEPARTAMENTO: 'departamento/UPDATE_DEPARTAMENTO',
  DELETE_DEPARTAMENTO: 'departamento/DELETE_DEPARTAMENTO',
  RESET: 'departamento/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDepartamento>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type DepartamentoState = Readonly<typeof initialState>;

// Reducer

export default (state: DepartamentoState = initialState, action): DepartamentoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DEPARTAMENTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DEPARTAMENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DEPARTAMENTO):
    case REQUEST(ACTION_TYPES.UPDATE_DEPARTAMENTO):
    case REQUEST(ACTION_TYPES.DELETE_DEPARTAMENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DEPARTAMENTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DEPARTAMENTO):
    case FAILURE(ACTION_TYPES.CREATE_DEPARTAMENTO):
    case FAILURE(ACTION_TYPES.UPDATE_DEPARTAMENTO):
    case FAILURE(ACTION_TYPES.DELETE_DEPARTAMENTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEPARTAMENTO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEPARTAMENTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DEPARTAMENTO):
    case SUCCESS(ACTION_TYPES.UPDATE_DEPARTAMENTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DEPARTAMENTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/departamentos';

// Actions

export const getEntities: ICrudGetAllAction<IDepartamento> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DEPARTAMENTO_LIST,
  payload: axios.get<IDepartamento>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IDepartamento> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DEPARTAMENTO,
    payload: axios.get<IDepartamento>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDepartamento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DEPARTAMENTO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDepartamento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DEPARTAMENTO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDepartamento> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DEPARTAMENTO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
